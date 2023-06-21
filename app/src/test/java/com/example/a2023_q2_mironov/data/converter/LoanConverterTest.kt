package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.utils.LoanData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoanConverterTest {

    private val converter = LoanConverter()

    private val loan = LoanData.loanEntity
    private val loanDto = LoanData.loanDto

    @Test
    fun `convert loan EXPECT loan dto`(){

        val expected = loan
        val actual = converter.revert(loanDto)

        assertEquals(expected, actual)
    }

    @Test
    fun `convert loan dto EXPECT loan`(){

        val expected = loanDto
        val actual = converter.convert(loan)

        assertEquals(expected, actual)
    }
}