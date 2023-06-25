package com.example.a2023_q2_mironov.presentation.login

import com.example.a2023_q2_mironov.domain.entity.AuthErrorType

sealed interface LoginState {

    object Initial : LoginState

    object Loading : LoginState

    data class Error(val type: AuthErrorType) : LoginState
}