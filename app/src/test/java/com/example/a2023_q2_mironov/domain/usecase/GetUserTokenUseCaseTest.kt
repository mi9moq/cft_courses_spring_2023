package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class GetUserTokenUseCaseTest {

    private val repository: UserTokenRepository = mock()
    private val useCase = GetUserTokenUseCase(repository)
    private val testToken = AccessUserToken("user token")

    @Test
    fun `invoke EXPECT user token`() {
        whenever(repository.getUserToken()) doReturn testToken

        val expected = testToken
        val actual = useCase()

        assertEquals(expected, actual)
    }
}