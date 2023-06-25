package com.example.a2023_q2_mironov.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.DetailsRouter
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.presentation.details.DetailsState.Content
import com.example.a2023_q2_mironov.presentation.details.DetailsState.Error
import com.example.a2023_q2_mironov.presentation.details.DetailsState.Initial
import com.example.a2023_q2_mironov.presentation.details.DetailsState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val resetUserTokenUseCase: ResetUserTokenUseCase,
    private val router: DetailsRouter,
) : ViewModel() {

    private val _state: MutableLiveData<DetailsState> = MutableLiveData(Initial)
    val state: LiveData<DetailsState> = _state

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

    fun loadDetails(id: Long) {
        val token = getUserTokenUseCase().userToken
        _state.value = Loading
        viewModelScope.launch(handler) {
            val loan = getLoanByIdUseCase(token, id)
            _state.value = Content(loan)
        }
    }

    fun relogin() {
        resetUserTokenUseCase()
        router.openWelcome()
    }
}