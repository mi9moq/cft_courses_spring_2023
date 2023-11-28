package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.LoanData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class CreateLoanUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = CreateLoanUseCase(repository)

    private val loanRequest = LoanData.loanRequestEntity
    private val token = AuthData.token

    @Test
    fun `invoke EXPECT create loan`() = runTest {

        useCase(token, loanRequest)

        verify(repository).create(token, loanRequest)
    }
}