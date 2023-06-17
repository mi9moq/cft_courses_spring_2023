package com.example.a2023_q2_mironov.presentation.login

sealed interface LoginState

object Initial : LoginState

object Loading: LoginState

data class Error(val message: String): LoginState


