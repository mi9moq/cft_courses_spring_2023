package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getCreateScreen
import com.example.a2023_q2_mironov.navigation.screen.getDetailsScreen
import com.example.a2023_q2_mironov.navigation.screen.getWelcomeScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface HistoryRouter {

    fun openDetails(id: Long)

    fun openCreate()

    fun openWelcome()
}

class HistoryRouterImpl @Inject constructor(
    private val router: Router
):HistoryRouter {

    override fun openDetails(id: Long) {
        router.navigateTo(getDetailsScreen(id))
    }

    override fun openCreate() {
        router.replaceScreen(getCreateScreen())
    }

    override fun openWelcome() {
        router.newRootScreen(getWelcomeScreen())
    }
}
