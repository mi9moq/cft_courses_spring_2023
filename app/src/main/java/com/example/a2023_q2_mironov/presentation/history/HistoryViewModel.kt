package com.example.a2023_q2_mironov.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.usecase.GetAllLoansUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.HistoryRouter
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.history.HistoryState.*
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
    private val router: HistoryRouter
) : ViewModel() {

    private val _state: MutableLiveData<HistoryState> = MutableLiveData(Initial)
    val state: LiveData<HistoryState> = _state

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> _state.value =
                Error(ErrorType.CONNECTION)

            is HttpException -> {
                if (throwable.code() == 401)
                    _state.value = Error(ErrorType.UNAUTHORIZED)
                else if (throwable.code() == 404)
                    _state.value = Error(ErrorType.NOT_FOUND)
            }

            else -> _state.value = Error(ErrorType.UNKNOWN)
        }
    }

    init {
        loadHistory()
    }

    fun openLoanDetail(id: Long) {
        router.openDetails(id)
    }

    private fun loadHistory() {
        val token = getUserTokenUseCase().userToken
        _state.value = Loading
        viewModelScope.launch(handler) {
            val loans = getAllLoansUseCase(token)
            _state.value = Content(loans)
        }
    }
}