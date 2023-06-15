package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User
import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(auth: Auth): User = repository.registration(auth)
}