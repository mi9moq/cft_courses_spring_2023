package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class SetUserTokenUseCaseTest {

    private val repository: UserTokenRepository = Mockito.mock()
    private val useCase = SetUserTokenUseCase(repository)
    private val testToken = AccessUserToken("user token")

    @Test
    fun `invoke EXPECT set user token`() {

        useCase(testToken)

        verify(repository).setUserToken(testToken)
    }
}