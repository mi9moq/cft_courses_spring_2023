package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val dataSource: LoanRemoteDataSource
) : LoanRepository {

    override suspend fun createLoan(token: String, loan: LoanRequest) {
        dataSource.createLoan(token, loan)
    }

    override suspend fun getAllLoans(token: String): List<Loan> =
        dataSource.getAllLoans(token)

    override suspend fun getLoanById(token: String, id: Long): Loan =
        dataSource.getLoanById(token, id)

    override suspend fun getLoanConditions(token: String): LoanConditions =
        dataSource.getLoanConditions(token)
}