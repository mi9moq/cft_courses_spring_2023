package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.ui.approved.LoanApprovedFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getApprovedScreen() = FragmentScreen {
    LoanApprovedFragment.newInstance()
}