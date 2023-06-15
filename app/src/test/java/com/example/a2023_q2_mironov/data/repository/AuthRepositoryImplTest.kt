package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_mironov.utils.AuthData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class AuthRepositoryImplTest {

    private val dataSource: AuthRemoteDataSource = mock()
    private val repository = AuthRepositoryImpl(dataSource)

    private val auth = AuthData.authEntity
    private val user = AuthData.userEntity
    private val token = AuthData.token

    @Test
    fun `registration EXPECT user`() = runTest {
        whenever(dataSource.registration(auth)) doReturn user

        val expected = user
        val actual = repository.registration(auth)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login EXPECT token`() = runTest {
        whenever(dataSource.login(auth)) doReturn token

        val expected = token
        val actual = repository.login(auth)

        Assertions.assertEquals(expected, actual)
    }
}