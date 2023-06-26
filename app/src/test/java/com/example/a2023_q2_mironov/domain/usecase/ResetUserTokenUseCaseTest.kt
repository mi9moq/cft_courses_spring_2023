package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class ResetUserTokenUseCaseTest {

    private val repository: UserTokenRepository = Mockito.mock()
    private val useCase = ResetUserTokenUseCase(repository)

    @Test
    fun `invoke EXPECT rest token`() {

        useCase()

        verify(repository).resetUserToken()
    }
}