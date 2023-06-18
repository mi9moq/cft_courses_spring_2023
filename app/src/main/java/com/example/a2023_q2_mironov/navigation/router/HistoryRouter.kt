package com.example.a2023_q2_mironov.navigation.router

import com.example.a2023_q2_mironov.navigation.screen.getDetailsScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface HistoryRouter {

    fun openDetails(id: Long)
}

class HistoryRouterImpl @Inject constructor(
    private val router: Router
):HistoryRouter {

    override fun openDetails(id: Long) {
        router.navigateTo(getDetailsScreen(id))
    }
}