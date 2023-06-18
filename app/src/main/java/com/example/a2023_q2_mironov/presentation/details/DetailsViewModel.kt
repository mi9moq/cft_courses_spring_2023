package com.example.a2023_q2_mironov.presentation.details

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase
) : ViewModel() {

    fun loadDetails(id: Long) {

    }
}