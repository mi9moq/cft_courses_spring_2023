package com.example.a2023_q2_mironov.data.datasource

import com.example.a2023_q2_mironov.data.converter.LoanConditionsConverter
import com.example.a2023_q2_mironov.data.converter.LoanConverter
import com.example.a2023_q2_mironov.data.converter.LoanRequestConverter
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.LoanData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class LoanRemoteDataSourceImplTest {

    private val loanConverter: LoanConverter = mock()
    private val loanRequestConverter: LoanRequestConverter = mock()
    private val loanConditionsConverter: LoanConditionsConverter = mock()
    private val api: LoanApi = mock()

    private val token = AuthData.token
    private val loansDto = LoanData.loansDtoList
    private val loans = LoanData.loansList
    private val loanDto = LoanData.loanDto
    private val loan = LoanData.loanEntity
    private val loanId = 111L
    private val conditionsDto = LoanData.loanConditionsDto
    private val conditions = LoanData.loanConditionsEntity
    private val request = LoanData.loanRequestEntity
    private val requestDto = LoanData.loanRequestDto

    @Test
    fun `getAll EXPECT loans list`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            api,
            testDispatcher,
            loanConverter,
            loanConditionsConverter,
            loanRequestConverter
        )
        whenever(api.getAll(token)) doReturn loansDto
        whenever(loanConverter.revert(loanDto)) doReturn loan

        val expected = loans
        val actual = dataSource.getAll(token)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan by id EXPECT loan`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            api,
            testDispatcher,
            loanConverter,
            loanConditionsConverter,
            loanRequestConverter
        )
        whenever(api.getLoanById(token, loanId)) doReturn loanDto
        whenever(loanConverter.revert(loanDto)) doReturn loan

        val expected = loan
        val actual = dataSource.getLoanById(token, loanId)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan conditions EXPECT loan conditions`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            api,
            testDispatcher,
            loanConverter,
            loanConditionsConverter,
            loanRequestConverter
        )
        whenever(api.getLoanConditions(token)) doReturn conditionsDto
        whenever(loanConditionsConverter.revert(conditionsDto)) doReturn conditions

        val expected = conditions
        val actual = dataSource.getConditions(token)

        assertEquals(expected, actual)
    }

    @Test
    fun `create EXPECT create loan`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            api,
            testDispatcher,
            loanConverter,
            loanConditionsConverter,
            loanRequestConverter
        )
        whenever(loanRequestConverter.convert(request)) doReturn requestDto

        dataSource.create(token, request)

        verify(api).createLoan(token, requestDto)
    }
}