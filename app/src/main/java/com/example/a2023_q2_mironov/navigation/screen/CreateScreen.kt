package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.create.CreateLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getCreateScreen() = FragmentScreen {
    CreateLoanFragment.newInstance()
}