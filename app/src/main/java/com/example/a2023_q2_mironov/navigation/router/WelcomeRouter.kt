package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getLoginScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface WelcomeRouter {

    fun openLogin()

    fun openRegistration()

    fun openMain()
}

class WelcomeRouterImpl @Inject constructor(
    private val router: Router
) : WelcomeRouter {

    override fun openLogin() {
        router.navigateTo(getLoginScreen())
    }

    override fun openRegistration() {
        TODO("Not yet implemented")
    }

    override fun openMain() {
        TODO("Not yet implemented")
    }
}