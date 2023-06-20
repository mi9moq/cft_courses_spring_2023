package com.example.a2023_q2_mironov.di.module

import com.example.a2023_q2_mironov.di.AppScope
import com.example.a2023_q2_mironov.navigation.router.GuidRouter
import com.example.a2023_q2_mironov.navigation.router.GuidRouterImpl
import com.example.a2023_q2_mironov.navigation.router.HistoryRouter
import com.example.a2023_q2_mironov.navigation.router.HistoryRouterImpl
import com.example.a2023_q2_mironov.navigation.router.LoginRouter
import com.example.a2023_q2_mironov.navigation.router.LoginRouterImpl
import com.example.a2023_q2_mironov.navigation.router.MainRouter
import com.example.a2023_q2_mironov.navigation.router.MainRouterImpl
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouter
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouterImpl
import com.example.a2023_q2_mironov.navigation.router.WelcomeRouter
import com.example.a2023_q2_mironov.navigation.router.WelcomeRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NavigationModule {

    companion object {

        private val cicerone: Cicerone<Router> = create()

        @Provides
        @AppScope
        fun provideRouter(): Router {
            return cicerone.router
        }

        @Provides
        @AppScope
        fun provideNavigatorHolder(): NavigatorHolder {
            return cicerone.getNavigatorHolder()
        }
    }

    @Binds
    @AppScope
    fun bindWelcomeRouter(impl: WelcomeRouterImpl): WelcomeRouter

    @Binds
    @AppScope
    fun bindRegistrationRouter(impl: RegistrationRouterImpl): RegistrationRouter

    @Binds
    @AppScope
    fun bindLoginRouter(impl: LoginRouterImpl): LoginRouter

    @Binds
    @AppScope
    fun bindMainRouter(impl: MainRouterImpl): MainRouter

    @Binds
    @AppScope
    fun bindHistoryRouter(impl: HistoryRouterImpl): HistoryRouter

    @Binds
    @AppScope
    fun bindGuidRouter(impl: GuidRouterImpl): GuidRouter
}