package com.neshchereto.abitsearch.domain.usecase

import org.junit.Assert.*
import org.junit.Test

class ValidateInputUseCaseTest {

    @Test
    fun validateSearch() {
        val validateUseCase = ValidateInputUseCase()

        val expected = true
        //val actual = validateUseCase.isValid("Петрова В. О. 2022")
        val actual = validateUseCase.isValid("Петрова В. О.")
        // val actual = validateUseCase.isValid("Петрова В. О. 10.3 2022")
        assertEquals(expected, actual)
    }
}