package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.LoginFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getLoginScreen() = FragmentScreen {
    LoginFragment.newInstance()
}