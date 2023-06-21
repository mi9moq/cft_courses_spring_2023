package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.LoanData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GetLoanByIdUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetLoanByIdUseCase(repository)

    private val id = 111L
    private val loan = LoanData.loanEntity
    private val token = AuthData.token

    @Test
    fun `invoke EXPECT loan`() = runTest {
        whenever(repository.getLoanById(token, id)) doReturn loan

        val expected = loan
        val actual = useCase(token,id)

        Assertions.assertEquals(expected, actual)
    }
}