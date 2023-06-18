package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getMainScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface LoginRouter {

    fun openMain()
}

class LoginRouterImpl @Inject constructor(
    private val router: Router
): LoginRouter{

    override fun openMain() {
        router.newRootScreen(getMainScreen())
    }
}