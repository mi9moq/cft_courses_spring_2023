package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.guid.GuidFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getGuidScreen() = FragmentScreen {
    GuidFragment.newInstance()
}