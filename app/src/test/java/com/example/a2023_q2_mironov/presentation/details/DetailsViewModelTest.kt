package com.example.a2023_q2_mironov.presentation.details

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.DetailsRouter
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.LoanData
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class DetailsViewModelTest {

    private val getUserTokenUseCase: GetUserTokenUseCase = mock()
    private val getLoanByIdUseCase: GetLoanByIdUseCase = mock()
    private val resetUserTokenUseCase: ResetUserTokenUseCase = mock()
    private val router: DetailsRouter = mock()
    private val stateObserver: Observer<DetailsState> = mock()
    private val viewModel = DetailsViewModel(
        getUserTokenUseCase,
        getLoanByIdUseCase,
        resetUserTokenUseCase,
        router
    )

    private val loan = LoanData.loanEntity
    private val id = 101L
    private val token = AuthData.token

    @Test
    fun `loadDetails EXPECT content state`() = runTest {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)
        whenever(getLoanByIdUseCase(token, id)) doReturn loan
        viewModel.state.observeForever(stateObserver)

        viewModel.loadDetails(id)

        inOrder(stateObserver){
            verify(stateObserver).onChanged(DetailsState.Initial)
            verify(stateObserver).onChanged(DetailsState.Loading)
            verify(stateObserver).onChanged(DetailsState.Content(loan))
        }
    }

    @Test
    fun `relogin EXPECT reset token and navigate`() {

        viewModel.relogin()

        verify(resetUserTokenUseCase).invoke()
        verify(router).openWelcome()
    }
}