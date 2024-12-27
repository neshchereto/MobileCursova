package com.neshchereto.abitsearch.data.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GoogleSheetsDto(
    val version:String?=null,
    val reqId:String?=null,
    val status:String?=null,
    val sig:String?=null,
    val table:TableData?=null
    ){
    @Serializable
    data class TableData(
        val cols: List<ColData?>?=null,
        val rows:List<RowData?>?=null,
        val parsedNumHeaders: Int? = null
    ){
        @Serializable
        data class ColData(
            val id: String? = null,
            val label: String? = null,
            val type: String? = null,
            val pattern: String? = null
        )

        @Serializable
        data class RowData(
            val c: List<Cell?>? = null
        ){
            @Serializable
            data class Cell(
                val v: JsonElement?=null,
                val f:String?=null
            )
        }
    }
}
