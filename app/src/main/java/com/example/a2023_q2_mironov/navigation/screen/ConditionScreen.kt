package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.fragment.CreateLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getConditionScreen() = FragmentScreen {
    CreateLoanFragment.newInstance()
}