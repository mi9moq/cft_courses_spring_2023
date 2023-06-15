package com.example.a2023_q2_mironov.data.datasource

import com.example.a2023_q2_mironov.data.converter.UserConverter
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.utils.AuthData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class AuthRemoteDataSourceImplTest {

    private val converter: UserConverter = mock()
    private val api: LoanApi = mock()

    private val auth = AuthData.auth
    private val userDto = AuthData.userDto
    private val user = AuthData.userEntity
    private val token = AuthData.token

    @Test
    fun `registration EXPECT get user`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = AuthRemoteDataSourceImpl(api, converter, testDispatcher)
        whenever(api.registration(auth.name, auth.password)) doReturn userDto
        whenever(converter.revert(userDto)) doReturn user

        val expected = user
        val actual = dataSource.registration(auth)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `login EXPECT get user token`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = AuthRemoteDataSourceImpl(api, converter, testDispatcher)
        whenever(api.login(auth.name, auth.password)) doReturn token

        val expected = token
        val actual = dataSource.login(auth)

        Assertions.assertEquals(expected, actual)
    }
}