package com.example.a2023_q2_mironov.domain.repository

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest

interface LoanRepository {

    suspend fun createLoan(loan: LoanRequest)

    suspend fun getAllLoans(): List<Loan>

    suspend fun getLoanById(id: Long): Loan

    suspend fun getLoanConditions(): LoanConditions
}