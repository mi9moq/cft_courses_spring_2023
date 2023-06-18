package com.example.a2023_q2_mironov.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.presentation.details.DetailsState.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase
) : ViewModel() {

    private val _state: MutableLiveData<DetailsState> = MutableLiveData(Initial)
    val state: LiveData<DetailsState> = _state
    fun loadDetails(id: Long) {
        val token = getUserTokenUseCase().userToken
        _state.value = Loading
        viewModelScope.launch {
            val loan = getLoanByIdUseCase(token,id)
            _state.value = Content(loan)
        }
    }
}