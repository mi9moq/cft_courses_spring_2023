package com.example.a2023_q2_mironov.presentation.login

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.entity.AuthErrorType
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.LoginRouter
import com.example.a2023_q2_mironov.presentation.login.LoginState.*
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.stream.Stream

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class LoginViewModelErrorTest {

    private companion object {
        @JvmStatic
        fun connectionError(): Stream<Exception> = Stream.of(
            UnknownHostException(),
            ConnectException(),
            SocketTimeoutException()
        )
    }

    private val setUserTokenUseCase: SetUserTokenUseCase = Mockito.mock()
    private val loginUseCase: LoginUseCase = Mockito.mock()
    private val router: LoginRouter = Mockito.mock()
    private val viewModel = LoginViewModel(setUserTokenUseCase, loginUseCase, router)
    private val stateObserver: Observer<LoginState> = Mockito.mock()

    private val auth = AuthData.authEntity

    @Test
    fun `fields valid and login returns null pointer exception EXPECT error state not found`() =
        runTest {
            whenever(loginUseCase(auth)) doAnswer { throw NullPointerException() }

            viewModel.login(auth.name, auth.password)
            viewModel.state.observeForever(stateObserver)

            verify(stateObserver).onChanged(Error(AuthErrorType.WRONG_LOGIN_OR_PASSWORD))
        }

    @ParameterizedTest
    @MethodSource("connectionError")
    fun `fields valid and login returns connection exception EXPECT error state connection`(e: Exception) =
        runTest {
            whenever(loginUseCase(auth)) doAnswer { throw e }

            viewModel.login(auth.name, auth.password)
            viewModel.state.observeForever(stateObserver)

            verify(stateObserver).onChanged(Error(AuthErrorType.CONNECTION))
        }

    @Test
    fun `fields valid and login returns any other exception EXPECT error state unknown`() =
        runTest {
            whenever(loginUseCase(auth)) doAnswer { throw Exception() }

            viewModel.login(auth.name, auth.password)
            viewModel.state.observeForever(stateObserver)

            verify(stateObserver).onChanged(Error(AuthErrorType.UNKNOWN))
        }
}