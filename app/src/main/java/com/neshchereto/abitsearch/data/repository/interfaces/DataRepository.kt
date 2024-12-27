package com.neshchereto.abitsearch.data.repository.interfaces

import com.neshchereto.abitsearch.domain.model.EntrantTable

interface DataRepository {
    suspend fun getTableData(): EntrantTable?
}