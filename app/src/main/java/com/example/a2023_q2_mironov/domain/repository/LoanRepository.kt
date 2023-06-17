package com.example.a2023_q2_mironov.domain.repository

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest

interface LoanRepository {

    suspend fun createLoan(userToken: String, loan: LoanRequest)

    suspend fun getAllLoans(userToken: String): List<Loan>

    suspend fun getLoanById(userToken: String, id: Long): Loan

    suspend fun getLoanConditions(userToken: String): LoanConditions
}