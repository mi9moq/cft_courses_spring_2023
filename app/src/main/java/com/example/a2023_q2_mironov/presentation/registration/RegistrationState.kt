package com.example.a2023_q2_mironov.presentation.registration

import com.example.a2023_q2_mironov.domain.entity.AuthErrorType

sealed interface RegistrationState {

    object Initial : RegistrationState

    object Loading : RegistrationState

    data class Error(val type: AuthErrorType) : RegistrationState
}