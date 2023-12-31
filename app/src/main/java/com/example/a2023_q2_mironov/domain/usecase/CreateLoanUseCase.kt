package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(
    private val repository: LoanRepository
) {
    suspend operator fun invoke(userToken: String, loan: LoanRequest) =
        repository.create(userToken, loan)
}