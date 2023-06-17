package com.example.a2023_q2_mironov.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.RegistrationUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(Initial)
    val state: LiveData<LoginState> = _state

    fun login(login: String, password: String) {
        val auth = Auth(login, password)
        viewModelScope.launch {
            loginUseCase(auth)
        }
    }

    fun register(login: String, password: String){
        val auth = Auth(login, password)
        viewModelScope.launch {
            registrationUseCase(auth)
        }
    }
}