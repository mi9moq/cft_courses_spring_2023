package com.example.a2023_q2_mironov.domain.usecase

import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import javax.inject.Inject

class ResetUserTokenUseCase @Inject constructor(
    private val repository: UserTokenRepository
) {
    operator fun invoke() = repository.resetUserToken()
}