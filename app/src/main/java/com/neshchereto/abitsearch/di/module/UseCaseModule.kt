package com.neshchereto.abitsearch.di.module

import com.neshchereto.abitsearch.domain.usecase.ValidateInputUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideValidateInputUseCase(): ValidateInputUseCase {
        return ValidateInputUseCase()
    }
}