package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getWelcomeScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface DetailsRouter {

    fun openWelcome()
}

class DetailsRouterImpl @Inject constructor(
    private val router: Router
) : DetailsRouter {

    override fun openWelcome() {
        router.newRootScreen(getWelcomeScreen())
    }
}