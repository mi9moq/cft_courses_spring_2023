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
class GetLoanConditionsUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetLoanConditionsUseCase(repository)

    private val token = AuthData.token
    private val conditions = LoanData.loanConditionsEntity

    @Test
    fun `invoke EXPECT loan conditions`() = runTest {
        whenever(repository.getLoanConditions(token)) doReturn conditions

        val expected = conditions
        val actual = useCase(token)

        Assertions.assertEquals(expected, actual)
    }
}