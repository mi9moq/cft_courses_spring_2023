package com.example.a2023_q2_mironov.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.LoginRouter
import com.example.a2023_q2_mironov.presentation.ErrorType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val router: LoginRouter
) : ViewModel() {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(Initial)
    val state: LiveData<LoginState> = _state

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean> = _errorInputPassword

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is NullPointerException -> _state.value = Error(ErrorType.NOT_FOUND)
            is UnknownHostException -> _state.value = Error(ErrorType.CONNECTION)
            is SocketTimeoutException -> _state.value = Error(ErrorType.CONNECTION)
            is ConnectException -> _state.value = Error(ErrorType.CONNECTION)
            else -> _state.value = Error(ErrorType.UNKNOWN)
        }
    }

    fun login(inputName: String?, inputPassword: String?) {
        val name = parseInput(inputName)
        val password = parseInput(inputPassword)
        val fieldsValid = validateInput(name, password)
        if (fieldsValid) {
            val auth = Auth(name, password)
            viewModelScope.launch(handler) {
                _state.value = Loading
                val token = loginUseCase(auth)
                setUserTokenUseCase(AccessUserToken(token))
                router.openMain()
            }
        }
    }

    private fun parseInput(input: String?): String = input?.trim() ?: ""

    private fun validateInput(name: String, password: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }
}