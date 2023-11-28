package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(
    private val repository: LoanRepository
) {
    suspend operator fun invoke(userToken: String): LoanConditions =
        repository.getConditions(userToken)
}