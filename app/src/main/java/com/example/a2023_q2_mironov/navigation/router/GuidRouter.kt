package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getMainScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface GuidRouter {

    fun openMain()
}

class GuidRouterImpl @Inject constructor(
    private val router: Router
) : GuidRouter {

    override fun openMain() {
        router.newRootScreen(getMainScreen())
    }
}