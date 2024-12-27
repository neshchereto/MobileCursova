package com.neshchereto.abitsearch.di.module

import com.neshchereto.abitsearch.data.remote.api.GoogleSheetsApiService
import com.neshchereto.abitsearch.data.repository.impl.DataRepositoryImpl
import com.neshchereto.abitsearch.data.repository.interfaces.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModel {

    @Provides
    fun provideDataRepository(
        apiService:GoogleSheetsApiService
    ): DataRepository = DataRepositoryImpl(apiService)

}