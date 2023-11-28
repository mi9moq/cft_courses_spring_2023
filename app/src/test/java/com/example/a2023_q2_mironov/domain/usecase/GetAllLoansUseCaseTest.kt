package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.LoanRepository
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.LoanData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class GetAllLoansUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetAllLoansUseCase(repository)

    private val token = AuthData.token
    private val loans = LoanData.loansList

    @Test
    fun `invoke EXPECT list loans`() = runTest {
        whenever(repository.getAll(token)) doReturn loans

        val expected = loans
        val actual = useCase(token)

        Assertions.assertEquals(expected, actual)
    }
}