package com.example.a2023_q2_mironov.presentation.registration

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.domain.usecase.LoginUseCase
import com.example.a2023_q2_mironov.domain.usecase.RegistrationUseCase
import com.example.a2023_q2_mironov.navigation.router.RegistrationRouter
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase,
    private val router: RegistrationRouter
): ViewModel() {
}