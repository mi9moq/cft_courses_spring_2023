package com.example.a2023_q2_mironov.navigation.screen

import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.ui.confirm.ConfirmLoanFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getConfirmScreen(request: LoanRequest) = FragmentScreen {
    ConfirmLoanFragment.newInstance(request)
}