package com.example.a2023_q2_mironov.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.usecase.GetAllLoansUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.HistoryRouter
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Content
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Error
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Initial
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val resetUserTokenUseCase: ResetUserTokenUseCase,
    private val router: HistoryRouter,
) : ViewModel() {

    private val _state: MutableLiveData<HistoryState> = MutableLiveData(Initial)
    val state: LiveData<HistoryState> = _state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> _state.value =
                Error(LoanErrorType.CONNECTION)

            is HttpException -> {
                if (throwable.code() == 403)
                    _state.value = Error(LoanErrorType.UNAUTHORIZED)
            }

            else -> _state.value = Error(LoanErrorType.UNKNOWN)
        }
    }

    init {
        loadHistory()
    }

    fun openLoanDetail(id: Long) {
        router.openDetails(id)
    }

    fun openCreate(){
        router.openCreate()
    }

    fun loadHistory() {
        val token = getUserTokenUseCase().userToken
        _state.value = Loading
        viewModelScope.launch(handler) {
            val loans = getAllLoansUseCase(token)
            _state.value = Content(loans)
        }
    }

    fun relogin(){
        resetUserTokenUseCase()
        router.openWelcome()
    }
}