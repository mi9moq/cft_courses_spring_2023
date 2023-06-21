package com.example.a2023_q2_mironov.presentation.welcome

import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.WelcomeRouter
import com.example.a2023_q2_mironov.presentation.welcom.WelcomeViewModel
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class WelcomeViewModelTest {

    private val router: WelcomeRouter = mock()
    private val getUserTokenUseCase: GetUserTokenUseCase = mock()
    private val viewModel = WelcomeViewModel(getUserTokenUseCase, router)

    private val token = "token"
    private val emptyToken = ""

    @Test
    fun `login user token is blank EXPECT open login`() {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(emptyToken)

        viewModel.login()

        verify(router).openLogin()
    }

    @Test
    fun `login user token is not blank EXPECT open main`() {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)

        viewModel.login()

        verify(router).openMain()
    }

    @Test
    fun `registration EXPECT open registration`() {

        viewModel.registration()

        verify(router).openRegistration()
    }
}