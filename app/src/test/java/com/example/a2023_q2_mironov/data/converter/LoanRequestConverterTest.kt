package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.utils.LoanData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoanRequestConverterTest {

    private val converter = LoanRequestConverter()

    private val loanRequest = LoanData.loanRequestEntity
    private val loanRequestDto = LoanData.loanRequestDto

    @Test
    fun `convert loan request EXPECTED loan request dto`(){

        val expected = loanRequestDto
        val actual = converter.convert(loanRequest)

        assertEquals(expected, actual)
    }
}