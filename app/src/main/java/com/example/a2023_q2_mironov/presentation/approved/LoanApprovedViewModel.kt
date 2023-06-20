package com.example.a2023_q2_mironov.presentation.approved

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.navigation.router.ApprovedRouter
import javax.inject.Inject

class LoanApprovedViewModel @Inject constructor(
    private val router: ApprovedRouter
) : ViewModel() {

    fun close() {
        router.backToMain()
    }
}