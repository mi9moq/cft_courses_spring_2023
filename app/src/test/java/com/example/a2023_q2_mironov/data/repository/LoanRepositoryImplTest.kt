package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.LoanData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class LoanRepositoryImplTest {

    private val dataSource: LoanRemoteDataSource = mock()
    private val repository = LoanRepositoryImpl(dataSource)

    private val token = AuthData.token
    private val loans = LoanData.loansList
    private val loan = LoanData.loanEntity
    private val loanId = 111L
    private val conditions = LoanData.loanConditionsEntity
    private val request = LoanData.loanRequestEntity

    @Test
    fun `get all EXPECT loans list`() = runTest {
        whenever(dataSource.getAllLoans(token)) doReturn loans

        val expected = loans
        val actual = repository.getAllLoans(token)

        assertEquals(expected, actual)
    }

    @Test
    fun `create EXEPCT create loan`() = runTest {

        repository.createLoan(token, request)

        verify(dataSource).createLoan(token, request)
    }

    @Test
    fun `get by id EXPECT loan`() = runTest {
        whenever(dataSource.getLoanById(token, loanId)) doReturn loan

        val expected = loan
        val actual = dataSource.getLoanById(token, loanId)

        assertEquals(expected, actual)
    }

    @Test
    fun `get conditions EXPECT conditions`() = runTest {
        whenever(dataSource.getLoanConditions(token)) doReturn conditions

        val expected = conditions
        val actual = repository.getLoanConditions(token)

        assertEquals(expected, actual)
    }
}