package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.RegistrationFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRegistrationScreen() = FragmentScreen {
    RegistrationFragment.newInstance()
}