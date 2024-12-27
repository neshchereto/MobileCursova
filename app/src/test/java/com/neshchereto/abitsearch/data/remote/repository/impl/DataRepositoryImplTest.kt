package com.neshchereto.abitsearch.data.remote.repository.impl

import com.neshchereto.abitsearch.data.remote.api.GoogleSheetsApiService
import com.neshchereto.abitsearch.data.repository.impl.DataRepositoryImpl
import com.neshchereto.abitsearch.di.module.HttpModule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class DataRepositoryImplTest{

    @Test
    fun shouldReturnTable()= runBlocking {
        val client = HttpModule.provideHttpClient()
        val service = GoogleSheetsApiService(client)
        val repo = DataRepositoryImpl(service)

        val actual = repo.getTableData()
        println(actual?.columnsName)
        println(actual?.rows)
        assertNotNull(actual)
    }
}