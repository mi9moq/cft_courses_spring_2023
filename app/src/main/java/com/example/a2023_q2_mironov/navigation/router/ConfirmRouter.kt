package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getApprovedScreen
import com.example.a2023_q2_mironov.navigation.screen.getMainScreen
import com.example.a2023_q2_mironov.navigation.screen.getWelcomeScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface ConfirmRouter {

    fun openApproved()

    fun openMain()

    fun openWelcome()
}

class ConfirmRouterImpl @Inject constructor(
    private val router: Router
) : ConfirmRouter {

    override fun openApproved() {
        router.navigateTo(getApprovedScreen())
    }

    override fun openMain() {
        router.navigateTo(getMainScreen())
    }

    override fun openWelcome() {
        router.newRootScreen(getWelcomeScreen())
    }
}