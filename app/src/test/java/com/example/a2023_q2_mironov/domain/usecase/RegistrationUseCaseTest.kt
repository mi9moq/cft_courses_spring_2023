package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import com.example.a2023_q2_mironov.utils.AuthData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class RegistrationUseCaseTest {

    private val repository: AuthRepository = mock()
    private val useCase = RegistrationUseCase(repository)

    private val user = AuthData.userEntity
    private val auth = AuthData.authEntity

    @Test
    fun `should return correct user data`() = runTest {
        whenever(repository.registration(auth)) doReturn user

        val expected = user
        val actual = useCase(auth)

        assertEquals(expected, actual)
    }
}