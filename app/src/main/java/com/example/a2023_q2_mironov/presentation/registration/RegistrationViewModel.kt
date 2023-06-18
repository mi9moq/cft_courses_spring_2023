package com.example.a2023_q2_mironov.presentation.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.RegistrationUseCase
import com.example.a2023_q2_mironov.domain.usecase.SetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouter
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.Initial
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase,
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val router: RegistrationRouter
) : ViewModel() {

    private val _state: MutableLiveData<RegistrationState> =
        MutableLiveData(Initial)
    val state: LiveData<RegistrationState> = _state

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean> = _errorInputPassword

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is HttpException -> {
                if(throwable.code() == 400){
                    _state.value = RegistrationState.Error(ErrorType.REGISTRATION)
                }
            }
            is UnknownHostException -> _state.value = RegistrationState.Error(ErrorType.CONNECTION)
            is SocketTimeoutException -> _state.value =
                RegistrationState.Error(ErrorType.CONNECTION)

            is ConnectException -> _state.value = RegistrationState.Error(ErrorType.CONNECTION)
            else -> _state.value = RegistrationState.Error(ErrorType.UNKNOWN)
        }
    }

    fun registration(inputName: String?, inputPassword: String?) {
        val name = parseInput(inputName)
        val password = parseInput(inputPassword)
        val fieldsValid = validateInput(name, password)
        if (fieldsValid) {
            viewModelScope.launch(handler) {
                _state.value = Loading
                val auth = Auth(name, password)
                registrationUseCase(auth)
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