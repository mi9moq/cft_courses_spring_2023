package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanByIdUseCase @Inject constructor(
    private val repository: LoanRepository
) {
    suspend operator fun invoke(id: Long): Loan = repository.getLoanById(id)
}