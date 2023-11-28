package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.welcome.WelcomeFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getWelcomeScreen() = FragmentScreen {
    WelcomeFragment.newInstance()
}