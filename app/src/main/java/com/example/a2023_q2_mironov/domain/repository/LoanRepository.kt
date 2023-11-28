package com.example.a2023_q2_mironov.domain.repository

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest

interface LoanRepository {

    suspend fun create(token: String, loan: LoanRequest)

    suspend fun getAll(token: String): List<Loan>

    suspend fun getLoanById(token: String, id: Long): Loan

    suspend fun getConditions(token: String): LoanConditions
}