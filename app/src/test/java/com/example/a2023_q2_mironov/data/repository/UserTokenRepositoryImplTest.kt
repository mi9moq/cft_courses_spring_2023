package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserTokenRepositoryImplTest {

    private val dataSource: TokenLocalDataSource = mock()
    private val repository = UserTokenRepositoryImpl(dataSource)

    private val testToken = AccessUserToken("user token")

    @Test
    fun `getUserToken EXPECT AccessUserToken`(){
        whenever(dataSource.get()) doReturn testToken

        val expected = testToken
        val actual = repository.getUserToken()

        assertEquals(expected, actual)
    }

    @Test
    fun `setUserToken EXPECT set to preferences`(){

        repository.setUserToken(testToken)

        verify(dataSource).set(testToken)
    }

    @Test
    fun `resetUserToken EXPECT rest token`(){

        repository.resetUserToken()

        verify(dataSource).reset()
    }
}