package com.example.a2023_q2_mironov.presentation.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.usecase.CreateLoanUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConfirmLoanViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ConfirmLoanState> = MutableLiveData(Initial)
    val state: LiveData<ConfirmLoanState> = _state

    private lateinit var request: LoanRequest

    fun putLoanRequest(loanRequest: LoanRequest) {
        request = loanRequest
        _state.value = Content(loanRequest)
    }

    fun createLoan() {
        val token = getUserTokenUseCase().userToken
        viewModelScope.launch {
            createLoanUseCase(token, request)
            //TODO показать, что займ успешно оформлен
        }
    }
}