package com.example.a2023_q2_mironov.presentation.login

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.LoginRouter
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class, InstantTaskExecutorExtension::class, TestCoroutineExtension::class)
class LoginViewModelTest {

    private val setUserTokenUseCase: SetUserTokenUseCase = mock()
    private val loginUseCase: LoginUseCase = mock()
    private val router: LoginRouter = mock()
    private val viewModel = LoginViewModel(setUserTokenUseCase, loginUseCase, router)
    private val stateObserver: Observer<LoginState> = mock()

    private val password = "password"
    private val login = "login"
    private val token = "token"

    @BeforeEach
    fun setUp() {
        viewModel.resetErrorInputName()
        viewModel.resetErrorInputPassword()
    }

    @Test
    fun `fields valid EXPECT open main`() = runTest {
        viewModel.state.observeForever(stateObserver)
        val auth = Auth(login,password)
        whenever(loginUseCase(auth)) doReturn token

        viewModel.login(login,password)

        verify(setUserTokenUseCase)(AccessUserToken(token))
        verify(router).openMain()
        inOrder(stateObserver){
            verify(stateObserver).onChanged(Initial)
            verify(stateObserver).onChanged(Loading)
        }
    }

    @Test
    fun `fields invalid EXPECT error input login`() = runTest {
        viewModel.state.observeForever(stateObserver)

        viewModel.login("","")

        verify(setUserTokenUseCase, never())(any())
        verify(router, never()).openMain()
        assertEquals(viewModel.errorInputName.value,true)
        assertEquals(viewModel.errorInputPassword.value,true)
        verify(stateObserver).onChanged(Initial)
    }
}