package com.example.a2023_q2_mironov.presentation.details

import androidx.lifecycle.Observer
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.DetailsRouter
import com.example.a2023_q2_mironov.utils.AuthData
import com.example.a2023_q2_mironov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_mironov.utils.TestCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.stream.Stream

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class DetailsViewModelErrorTest {

    private companion object {
        @JvmStatic
        fun connectionError(): Stream<Exception> = Stream.of(
            UnknownHostException(),
            ConnectException(),
            SocketTimeoutException()
        )
    }

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

    private val id = 101L
    private val token = AuthData.token
    private val responseBody = "some content".toResponseBody("plain/text".toMediaType())

    @Test
    fun `loadDetails EXPECT unknown error state`() = runTest {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)
        whenever(getLoanByIdUseCase(token, id)) doAnswer { throw Exception() }

        viewModel.loadDetails(id)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(DetailsState.Error(LoanErrorType.UNKNOWN))
    }

    @ParameterizedTest
    @MethodSource("connectionError")
    fun `loadDetails EXPECT connection error state`(e: Exception) = runTest {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)
        whenever(getLoanByIdUseCase(token, id)) doAnswer { throw e }

        viewModel.loadDetails(id)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(DetailsState.Error(LoanErrorType.CONNECTION))
    }

    @Test
    fun `loadDetails EXPECT unauthorized error state`() = runTest {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)
        whenever(getLoanByIdUseCase(token, id)) doAnswer {
            throw HttpException(Response.error<Any>(403, responseBody))
        }

        viewModel.loadDetails(id)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(DetailsState.Error(LoanErrorType.UNAUTHORIZED))
    }

    @Test
    fun `loadDetails http exception EXPECT unknown error state`() = runTest {
        whenever(getUserTokenUseCase()) doReturn AccessUserToken(token)
        whenever(getLoanByIdUseCase(token, id)) doAnswer {
            throw HttpException(Response.error<Any>(404, responseBody))
        }

        viewModel.loadDetails(id)
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(DetailsState.Error(LoanErrorType.UNKNOWN))
    }
}