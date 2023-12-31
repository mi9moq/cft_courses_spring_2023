package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.navigation.screen.getConfirmScreen
import com.example.a2023_q2_mironov.navigation.screen.getWelcomeScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface CreateRouter {

    fun openConfirm(request: LoanRequest)

    fun openWelcome()
}

class CreateRouterImpl @Inject constructor(
    private val router: Router
) : CreateRouter {

    override fun openConfirm(request: LoanRequest) {
        router.navigateTo(getConfirmScreen(request))
    }

    override fun openWelcome() {
        router.newRootScreen(getWelcomeScreen())
    }
}