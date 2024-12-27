package com.neshchereto.abitsearch.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neshchereto.abitsearch.domain.model.Entrant
import com.neshchereto.abitsearch.domain.model.EntrantTable
import com.neshchereto.abitsearch.domain.usecase.GetTableData
import com.neshchereto.abitsearch.domain.usecase.ValidateInputUseCase
import com.neshchereto.abitsearch.utils.enums.RequestStatus
import com.neshchereto.abitsearch.utils.internet.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTableData: GetTableData,
    private val connectivityObserver: ConnectivityObserver,
    private val validateInputUseCase: ValidateInputUseCase
) : ViewModel() {
    val networkStatus = connectivityObserver.status

    private val _entrantTable = MutableStateFlow<EntrantTable?>(null)
    val entrantTable: StateFlow<EntrantTable?> = _entrantTable

    private val _columnsName = MutableStateFlow<Entrant?>(null)
    val columnsName: StateFlow<Entrant?> = _columnsName

    private val _tableRows = MutableStateFlow<List<Entrant>>(emptyList())
    val tableRows: StateFlow<List<Entrant>> = _tableRows

    private val _previewRows = MutableStateFlow<Set<String>>(emptySet())
    val previewRows: StateFlow<Set<String>> = _previewRows

    var searchValue = mutableStateOf("")
    var isDropdownExpanded = mutableStateOf(false)

    init {
        connectivityObserver.start()
        loadTable()
    }

    fun onChangeDropdownExpanded(value:Boolean){
        isDropdownExpanded.value=value
    }

    fun onChangeValue(value: String) {
        searchValue.value = value

        val tempList = _entrantTable.value?.rows?.filter {
            it.name?.replace(
                "\"",
                ""
            )?.lowercase()?.contains(value.lowercase()) == true
        }
        val listOfNames = tempList?.map {
            it.name?.replace(
                "\"",
                ""
            )
        }
        if (!listOfNames.isNullOrEmpty()) {
            val temp = listOfNames.toSet() as Set<String>
            _previewRows.value = temp.take(3).toSet()
            if (value.length>0) onChangeDropdownExpanded(true) else onChangeDropdownExpanded(false)
        }else{
            _previewRows.value = emptySet()
        }

    }

    fun loadTable() {
        viewModelScope.launch(Dispatchers.IO) {
            while (networkStatus.value != ConnectivityObserver.Status.Available) {
                delay(100)
            }
            val fetchTable = getTableData.invoke() ?: null
            if (fetchTable != null) {
                _entrantTable.value = fetchTable
                _columnsName.value = fetchTable.columnsName
            }
        }
    }

    fun searchByName(): RequestStatus {
        val pattern = Regex(
            "^([А-ЩЬЮЯЇІЄҐ][а-щьюяїієґ']+\\s[А-ЩЬЮЯЇІЄҐ]\\.\\s?[А-ЩЬЮЯЇІЄҐ]\\.)(?:\\s(\\d+(?:\\.\\d+)?))?(?:\\s(\\d+(?:\\.\\d+)?))?\$"
        )
        if (validateInputUseCase.isValid(searchValue.value.trim())) {

            val matchResult = pattern.matchEntire(searchValue.value.trim())
            val name = matchResult?.groupValues?.get(1)
            val g2 = matchResult?.groupValues?.get(2)
            val g3 = matchResult?.groupValues?.get(3)

            val hasG2 = g2?.isNotBlank() ?: false
            val hasG3 = g3?.isNotBlank() ?: false


            if (hasG2 && hasG3) {

                if (g2?.contains('.') == true) {
                    val tempRes = _entrantTable.value?.rows?.filter {
                        it.name?.replace(
                            "\"",
                            ""
                        ) == name && it.averageScoreOfEducation?.replace(
                            "\"",
                            ""
                        ) == g2 && it.year?.replace("\"", "") == g3
                    }
                    if (!tempRes.isNullOrEmpty()) {
                        _tableRows.value = tempRes
                        return RequestStatus.SUCCESS
                    } else {
                        _tableRows.value = emptyList<Entrant>()
                        return RequestStatus.NO_MATCHES
                    }
                }

            } else if (hasG2) {
                if (g2?.contains('.') == false) {
                    val tempRes = _entrantTable.value?.rows?.filter {
                        it.name?.replace(
                            "\"",
                            ""
                        ) == name && it.year?.replace("\"", "") == g2
                    }
                    if (!tempRes.isNullOrEmpty()) {
                        _tableRows.value = tempRes
                        return RequestStatus.SUCCESS
                    } else {
                        _tableRows.value = emptyList<Entrant>()
                        return RequestStatus.NO_MATCHES
                    }
                }
            } else {
                val tempRes = _entrantTable.value?.rows?.filter {
                    it.name?.replace(
                        "\"",
                        ""
                    ) == name
                }
                println("COUTN OF: $tempRes ")
                if (!tempRes.isNullOrEmpty()) {
                    _tableRows.value = tempRes
                    return RequestStatus.SUCCESS
                } else {
                    _tableRows.value = emptyList<Entrant>()
                    return RequestStatus.NO_MATCHES
                }

            }

        }
        return RequestStatus.INCORRECT
    }

    override fun onCleared() {
        super.onCleared()
        connectivityObserver.stop()
    }


}