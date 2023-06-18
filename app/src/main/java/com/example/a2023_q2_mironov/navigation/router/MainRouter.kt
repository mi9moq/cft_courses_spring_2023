package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getHistoryScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface MainRouter {

    fun openHistory()

    fun openGuid()

    fun openCreate()
}

class MainRouterImpl @Inject constructor(
    private val router: Router
):MainRouter {

    override fun openHistory() {
        router.navigateTo(getHistoryScreen())
    }

    override fun openGuid() {
        TODO("Not yet implemented")
    }

    override fun openCreate() {
        TODO("Not yet implemented")
    }
}