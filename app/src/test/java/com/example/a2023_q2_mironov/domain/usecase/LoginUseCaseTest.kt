package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import com.example.a2023_q2_mironov.utils.AuthData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class LoginUseCaseTest {

    private val repository: AuthRepository = Mockito.mock()
    private val useCase = LoginUseCase(repository)

    private val auth = AuthData.auth

    private val token = AuthData.token

    @Test
    fun `should return correct token data`()= runTest {
        whenever(repository.login(auth)) doReturn token

        val expected = token
        val actual = useCase(auth)

        Assertions.assertEquals(expected, actual)
    }
}