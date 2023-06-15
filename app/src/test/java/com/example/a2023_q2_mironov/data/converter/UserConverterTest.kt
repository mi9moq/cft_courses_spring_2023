package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.utils.AuthData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserConverterTest {

    private val converter = UserConverter()

    private val userEntity = AuthData.userEntity
    private val userDto = AuthData.userDto

    @Test
    fun `convert user EXPECT user dto`() {
        val expected = userEntity
        val actual = converter.revert(userDto)

        assertEquals(expected, actual)
    }

    @Test
    fun `convert user dto EXPECT user`() {
        val expected = userDto
        val actual = converter.convert(userEntity)

        assertEquals(expected, actual)
    }
}