package com.example.a2023_q2_mironov.presentation.registration

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.RegistrationUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouter
import com.example.a2023_q2_mironov.presentation.ErrorType.*
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.*
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
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
class RegistrationViewModelErrorTest {

    private companion object {
        @JvmStatic
        fun connectionError(): Stream<Exception> = Stream.of(
            UnknownHostException(),
            ConnectException(),
            SocketTimeoutException()
        )
    }

    private val registrationUseCase: RegistrationUseCase = mock()
    private val loginUseCase: LoginUseCase = mock()
    private val setUserTokenUseCase: SetUserTokenUseCase = mock()
    private val router: RegistrationRouter = mock()
    private val stateObserver: Observer<RegistrationState> = mock()
    private val viewModel =
        RegistrationViewModel(registrationUseCase, loginUseCase, setUserTokenUseCase, router)

    private val auth = AuthData.authEntity
    private val token = AuthData.token


    @ParameterizedTest
    @MethodSource("connectionError")
    fun `fields valid and register returns connection exception EXPECT error state connection`(e: Exception) = runTest {
        whenever(viewModel.registration(auth.name,auth.password)) doAnswer {throw e}

        viewModel.registration(auth.name, auth.password)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(Error(CONNECTION))
    }

    @Test
    fun `fields valid and register returns any other exception EXPECT error state unknown`() =
        runTest {
            whenever(registrationUseCase(auth)) doAnswer { throw Exception() }

            viewModel.registration(auth.name, auth.password)
            viewModel.state.observeForever(stateObserver)

            verify(stateObserver).onChanged(Error(UNKNOWN))
        }
}