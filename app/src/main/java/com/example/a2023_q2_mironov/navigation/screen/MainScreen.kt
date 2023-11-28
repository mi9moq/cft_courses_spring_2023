package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.main.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getMainScreen() = FragmentScreen{
    MainFragment.newInstance()
}