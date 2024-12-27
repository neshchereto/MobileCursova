package com.neshchereto.abitsearch.data.repository.impl

import com.neshchereto.abitsearch.data.mappers.EntrantMapper
import com.neshchereto.abitsearch.data.remote.api.GoogleSheetsApiService
import com.neshchereto.abitsearch.data.remote.model.GoogleSheetsDto
import com.neshchereto.abitsearch.data.repository.interfaces.DataRepository
import com.neshchereto.abitsearch.domain.model.EntrantTable
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val apiService: GoogleSheetsApiService
): DataRepository {


    override suspend fun getTableData(): EntrantTable? {
        val googleSheetsResponse = apiService.fetchGoogleSheetData()
        val clearedJson = googleSheetsResponse
            .substringAfter("setResponse(")
            .substringBeforeLast(");")
            .trim()

        try{

            val dto =  Json.decodeFromString<GoogleSheetsDto>(clearedJson)
            println(dto.table?.cols)
            val entrantTable = EntrantMapper.mapToDomain(dto)
            return entrantTable
        }catch (e:Exception){
            e.printStackTrace()
            return    null
        }
    }

}