package com.example.a2023_q2_mironov.presentation.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.usecase.CreateLoanUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.ConfirmRouter
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.Content
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.Error
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.Initial
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ConfirmLoanViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val resetUserTokenUseCase: ResetUserTokenUseCase,
    private val router: ConfirmRouter,
) : ViewModel() {

    private val _state: MutableLiveData<ConfirmLoanState> = MutableLiveData(Initial)
    val state: LiveData<ConfirmLoanState> = _state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> _state.value =
                Error(LoanErrorType.CONNECTION)

            is HttpException -> {
                if (throwable.code() == 403)
                    _state.value = Error(LoanErrorType.UNAUTHORIZED)
                else
                    _state.value = Error(LoanErrorType.UNKNOWN)
            }

            else -> _state.value = Error(LoanErrorType.UNKNOWN)
        }
    }

    private lateinit var request: LoanRequest

    fun putLoanRequest(loanRequest: LoanRequest) {
        request = loanRequest
        _state.value = Content(loanRequest)
    }

    fun createLoan() {
        val token = getUserTokenUseCase().userToken
        viewModelScope.launch(handler) {
            _state.value = Loading
            createLoanUseCase(token, request)
            router.openApproved()
        }
    }

    fun backToMain() {
        router.openMain()
    }

    fun relogin() {
        resetUserTokenUseCase()
        router.openWelcome()
    }
}