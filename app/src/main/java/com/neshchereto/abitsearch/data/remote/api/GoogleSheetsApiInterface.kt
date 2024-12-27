package com.neshchereto.abitsearch.data.remote.api

interface GoogleSheetsApiInterface {
    suspend fun fetchGoogleSheetData():String
}