package com.neshchereto.abitsearch.presentation.screen.main

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neshchereto.abitsearch.presentation.screen.main.components.EntrantCard
import com.neshchereto.abitsearch.presentation.screen.main.components.InstructionDialog
import com.neshchereto.abitsearch.presentation.screen.main.components.NoInternetConnection
import com.neshchereto.abitsearch.presentation.viewmodel.MainViewModel
import com.neshchereto.abitsearch.utils.enums.RequestStatus
import com.neshchereto.abitsearch.utils.internet.ConnectivityObserver
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val columns by vm.columnsName.collectAsState()
    val table by vm.tableRows.collectAsState()
    val editTextValue by vm.searchValue
    var isOpenedInstruction by remember {
        mutableStateOf(false)
    }
    val networkStatus by vm.networkStatus.collectAsState()

    val previewRows by vm.previewRows.collectAsState()
    val isDropdownExpanded by vm.isDropdownExpanded


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchCard(
                value = editTextValue,
                isDropdownExpanded = isDropdownExpanded,
                onDismiss = {
                    vm.onChangeDropdownExpanded(false)
                },
                filteredSuggestions = previewRows,
                onValueChange = { vm.onChangeValue(it) },
                onSearch = {
                    val res = vm.searchByName()
                    when (res) {
                        RequestStatus.INCORRECT -> Toast.makeText(
                            context,
                            "У пошуковому запиті помилка",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        RequestStatus.NO_MATCHES -> Toast.makeText(
                            context,
                            "Немає збігів",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        RequestStatus.SUCCESS -> {}
                    }

                    coroutineScope.launch {
                        keyboardController?.hide()
                    }
                },
                onClearField = {
                    vm.onChangeValue("")
                    coroutineScope.launch {
                        keyboardController?.hide()
                    }
                },
                onOpenInstruction = {
                    isOpenedInstruction = true
                    coroutineScope.launch {
                        keyboardController?.hide()
                    }
                }
            )
            table.let { rows ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(rows) { item ->
                        columns?.let { EntrantCard(item = item, columns = it) }
                    }
                }
            }


        }

    }
    InstructionDialog(
        isVisible = isOpenedInstruction,
        onDismiss = { isOpenedInstruction = false }
    )

    NoInternetConnection(isVisible = networkStatus != ConnectivityObserver.Status.Available)

}



@Composable
private fun SearchCard(
    value: String,
    isDropdownExpanded: Boolean,
    onDismiss: () -> Unit,
    filteredSuggestions: Set<String>,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClearField: () -> Unit,
    onOpenInstruction: () -> Unit
) {


    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter){
                        OutlinedTextField(
                            value = value,
                            onValueChange = onValueChange,
                            label = { TextFieldLabel() },
                            trailingIcon = { TextFieldTrailingIcon(isVisible = value != "") { onClearField() } }
                        )
                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = onDismiss,
                            offset = DpOffset(x=16.dp, y=0.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            filteredSuggestions.forEach { suggestion ->
                                DropdownMenuItem(
                                    onClick = {
                                        onValueChange(suggestion)
                                        onDismiss()
                                    },
                                    text = { Text(text = suggestion) }
                                )
                            }
                        }
                    }

                    AnimatedVisibility(visible = value == "") {
                        IconButton(onClick = onOpenInstruction, modifier = Modifier.size(30.dp)) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                }
                Button(modifier = Modifier.fillMaxWidth(), onClick = onSearch) {
                    Text(text = "Пошук")
                }
            }

    }
}


@Composable
private fun TextFieldLabel() {
    Text(text = "ПІБ (рік вступу)")
}

@Composable
private fun TextFieldTrailingIcon(isVisible: Boolean, onDelete: () -> Unit) {
    AnimatedVisibility(visible = isVisible) {
        IconButton(onClick = onDelete) {
            Icon(Icons.Outlined.Clear, contentDescription = null)
        }
    }

}