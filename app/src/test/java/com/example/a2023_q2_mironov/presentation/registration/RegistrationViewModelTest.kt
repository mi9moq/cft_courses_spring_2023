package com.example.a2023_q2_mironov.presentation.registration

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.RegistrationUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouter
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.Initial
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.Loading
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class RegistrationViewModelTest {

    private val registrationUseCase: RegistrationUseCase = mock()
    private val loginUseCase: LoginUseCase = mock()
    private val setUserTokenUseCase: SetUserTokenUseCase = mock()
    private val router: RegistrationRouter = mock()
    private val stateObserver: Observer<RegistrationState> = Mockito.mock()
    private val viewModel =
        RegistrationViewModel(registrationUseCase, loginUseCase, setUserTokenUseCase, router)

    private val auth = AuthData.authEntity
    private val token = AuthData.token

    @BeforeEach
    fun setUp() {
        viewModel.resetErrorInputName()
        viewModel.resetErrorInputPassword()
    }

    @Test
    fun `fields valid EXPECT registration and login`() = runTest {
        viewModel.state.observeForever(stateObserver)
        whenever(registrationUseCase(auth)) doReturn AuthData.userEntity
        whenever(loginUseCase(auth)) doReturn token

        viewModel.registration(auth.name, auth.password)

        verify(registrationUseCase)(auth)
        verify(loginUseCase)(auth)
        verify(setUserTokenUseCase)(AccessUserToken(token))
        verify(router).openMain()
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(Initial)
            verify(stateObserver).onChanged(Loading)
        }
    }

    @Test
    fun `fields invalid EXPECT error input login`() = runTest {

        viewModel.state.observeForever(stateObserver)
        viewModel.registration("", "")

        verify(loginUseCase, never())(auth)
        verify(setUserTokenUseCase, never())(any())
        verify(router, never()).openMain()
        assertEquals(viewModel.errorInputName.value, true)
        assertEquals(viewModel.errorInputPassword.value, true)
        verify(stateObserver).onChanged(Initial)
    }
}