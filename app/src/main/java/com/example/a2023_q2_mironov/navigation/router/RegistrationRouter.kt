package com.example.a2023_q2_mironov.navigation.router

import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface RegistrationRouter {

    fun openMain()
}

class RegistrationRouterImpl @Inject constructor(
    private val router: Router
): RegistrationRouter{

    override fun openMain() {
        TODO("Not yet implemented")
    }
}