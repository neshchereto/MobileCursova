package com.neshchereto.abitsearch.domain.usecase

import com.neshchereto.abitsearch.data.repository.interfaces.DataRepository
import com.neshchereto.abitsearch.domain.model.EntrantTable
import javax.inject.Inject

class GetTableData @Inject constructor(
    private val dataRepo: DataRepository
) {
    suspend fun invoke():EntrantTable?{
        return dataRepo.getTableData()
    }
}