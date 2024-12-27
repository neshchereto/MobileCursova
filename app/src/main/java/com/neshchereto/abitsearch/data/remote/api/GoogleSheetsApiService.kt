package com.neshchereto.abitsearch.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class GoogleSheetsApiService @Inject constructor(
    private val client: HttpClient,
) : GoogleSheetsApiInterface {

    private val SHEET_ID = "19Hc0JlJFfUqW36pSswcSosm3Jt1SYse2WCdY4sgB7-E"

    override suspend fun fetchGoogleSheetData(): String {
        val url = "https://docs.google.com/spreadsheets/d/${SHEET_ID}/gviz/tq?tqx=out:json"
        return client.get(url).bodyAsText()
    }

}