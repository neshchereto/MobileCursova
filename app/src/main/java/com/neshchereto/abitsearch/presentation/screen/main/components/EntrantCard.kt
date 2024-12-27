package com.neshchereto.abitsearch.presentation.screen.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neshchereto.abitsearch.domain.model.Entrant


@Composable
fun EntrantCard(item: Entrant, columns: Entrant) {

    var isDetailsVisible by remember { mutableStateOf(false) }

    Card() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                println(item)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EntrantParameterTitle(
                        columnName = columns.qualificationLevel,
                        rowValue =  item.qualificationLevel
                        )


                }
                Column(
                    modifier = Modifier.weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EntrantParameterTitle(
                        columnName = columns.name,
                        rowValue =  item.name
                    )
                }
                Column(
                    modifier = Modifier.weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EntrantParameterTitle(
                        columnName = columns.nameOfInstitution,
                        rowValue =  item.nameOfInstitution
                    )
                }
            }
            AnimatedVisibility(isDetailsVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.status,
                            rowValue = item.status
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.priority,
                            rowValue = item.priority
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameterWithList(
                            columnName = columns.numberOfPlaces,
                            rowValue = item.numberOfPlaces
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.competitiveScore,
                            rowValue = item.competitiveScore
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.averageScoreOfEducation,
                            rowValue = item.averageScoreOfEducation
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameterWithList(
                            columnName = columns.componentsOfCompetitiveScore,
                            rowValue = item.componentsOfCompetitiveScore
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.faculty,
                            rowValue = item.faculty
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.specialty,
                            rowValue = item.specialty
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.quota,
                            rowValue = item.quota
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EntrantParameter(
                            columnName = columns.documentsSubmitted,
                            rowValue = item.documentsSubmitted
                        )
                    }
                }


            }




            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End

            ) {
                Button(onClick = { isDetailsVisible = !isDetailsVisible }) {
                    Text(text = if (isDetailsVisible) "Приховати" else "Детальніше")

                    if (!isDetailsVisible){
                        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
                    }else{
                        Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = null)
                    }
                }
            }
        }


    }
}

@Composable
private fun RowScope.EntrantParameter(columnName: String?, rowValue: String?) {

    columnName?.let {
        Text(
            text = it.replace(
                "\"",
                ""
            ) + ": ", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W800, fontSize = 15.sp)
        )
    }
    rowValue?.let {
        if (it != "null") Text(
            text = it.replace(
                "\"",
                ""
            )
        ) else Text(
            text = "-"
        )
    }

}
@Composable
private fun ColumnScope.EntrantParameterTitle(columnName: String?, rowValue: String?) {

    columnName?.let {
        Text(
            text = it.replace(
                "\"",
                ""
            ), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W800, fontSize = 18.sp)
        )
    }
    rowValue?.let {
        if (it != "null") Text(
            text = it.replace(
                "\"",
                ""
            )
        ) else Text(
            text = "-"
        )
    }

}

@Composable
private fun ColumnScope.EntrantParameterWithList(columnName: String?, rowValue: String?) {

    columnName?.let {
        Text(
            text = it.replace(
                "\"",
                ""
            ) + ": ", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W800, fontSize = 15.sp)
        )
    }
    rowValue?.let {
        if (it != "null") {
            val list = transformStringToListOfStrings(rowValue)
            list.forEach { el ->
                Text(
                    text = el.replace(
                        "\"",
                        ""
                    )
                )
            }

        } else Text(
            text = "-"
        )
    }
}

fun transformStringToListOfStrings(input: String): List<String> {
    val result = mutableListOf<String>()
    var part = ""

    input.forEach { el ->
        if (el != ';') {
            if (el != '=') {
                part += el
            } else {
                part += ": "
            }
        } else {
            result.add(part)
            part = ""
        }
    }
    return result
}