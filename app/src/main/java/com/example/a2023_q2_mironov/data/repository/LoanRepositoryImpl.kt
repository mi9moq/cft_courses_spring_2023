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

    override suspend fun create(token: String, loan: LoanRequest) {
        dataSource.create(token, loan)
    }

    override suspend fun getAll(token: String): List<Loan> =
        dataSource.getAll(token)

    override suspend fun getLoanById(token: String, id: Long): Loan =
        dataSource.getLoanById(token, id)

    override suspend fun getConditions(token: String): LoanConditions =
        dataSource.getConditions(token)
}