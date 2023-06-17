package com.example.a2023_q2_mironov.presentation.welcom

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.WelcomeRouter
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val router: WelcomeRouter
) : ViewModel() {

    fun login() {
        if (getUserTokenUseCase().userToken.isNotBlank()) {
            //TODO навигироваться на главный экран
        } else {
            router.openLogin()
        }
    }

    fun registration() {
        router.openRegistration()
    }
}