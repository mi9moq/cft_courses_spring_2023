package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.utils.LoanData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LoanConditionsConverterTest {

    private val converter = LoanConditionsConverter()

    private val conditionsDto = LoanData.loanConditionsDto
    private val conditionsEntity = LoanData.loanConditionsEntity

    @Test
    fun `covert condition EXPECT conditions dto`() {

        val expected = conditionsDto
        val actual = converter.convert(conditionsEntity)

        assertEquals(expected, actual)
    }

    @Test
    fun `convert conditions dto EXPECT conditions`() {

        val expected = conditionsEntity
        val actual = converter.revert(conditionsDto)

        assertEquals(expected, actual)
    }
}