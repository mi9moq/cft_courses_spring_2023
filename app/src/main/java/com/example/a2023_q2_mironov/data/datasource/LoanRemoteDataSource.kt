package com.example.a2023_q2_mironov.data.datasource

import com.example.a2023_q2_mironov.data.converter.LoanConditionsConverter
import com.example.a2023_q2_mironov.data.converter.LoanConverter
import com.example.a2023_q2_mironov.data.converter.LoanRequestConverter
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.di.IoDispatcher
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LoanRemoteDataSource {

    suspend fun create(token: String, loan: LoanRequest)

    suspend fun getAll(token: String): List<Loan>

    suspend fun getLoanById(token: String, id: Long): Loan

    suspend fun getConditions(token: String): LoanConditions
}

class LoanRemoteDataSourceImpl @Inject constructor(
    private val api: LoanApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loanConverter: LoanConverter,
    private val conditionsConverter: LoanConditionsConverter,
    private val requestConverter: LoanRequestConverter,
) : LoanRemoteDataSource {

    override suspend fun create(token: String, loan: LoanRequest) {
        withContext(ioDispatcher) {
            api.createLoan(token, loan.let(requestConverter::convert))
        }
    }

    override suspend fun getAll(token: String): List<Loan> = withContext(ioDispatcher) {
        api.getAll(token).map(loanConverter::revert)
    }

    override suspend fun getLoanById(token: String, id: Long): Loan = withContext(ioDispatcher) {
        api.getLoanById(token, id).let(loanConverter::revert)
    }


    override suspend fun getConditions(token: String): LoanConditions =
        withContext(ioDispatcher) {
            api.getLoanConditions(token).let(conditionsConverter::revert)
        }
}