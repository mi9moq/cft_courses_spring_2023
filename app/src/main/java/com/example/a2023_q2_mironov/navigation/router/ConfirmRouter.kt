package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getApprovedScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface ConfirmRouter {

    fun openApproved()
}

class ConfirmRouterImpl @Inject constructor(
    private val router: Router
): ConfirmRouter{

    override fun openApproved() {
        router.navigateTo(getApprovedScreen())
    }
}