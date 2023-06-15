package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.utils.AuthData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthConverterTest {

    private val converter = AuthConverter()

    private val authEntity = AuthData.authEntity
    private val authDto = AuthData.authDto

    @Test
    fun `convert auth EXPECT auth dto`() {
        val expected = authEntity
        val actual = converter.revert(authDto)

        assertEquals(expected, actual)
    }

    @Test
    fun `convert auth dto EXPECT auth`() {
        val expected = authDto
        val actual = converter.convert(authEntity)

        assertEquals(expected, actual)
    }
}