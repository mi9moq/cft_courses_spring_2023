package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getMainScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface ApprovedRouter {

    fun backToMain()
}

class ApprovedRouterImpl @Inject constructor(
    private val router: Router
): ApprovedRouter{

    override fun backToMain() {
        router.backTo(getMainScreen())
    }
}