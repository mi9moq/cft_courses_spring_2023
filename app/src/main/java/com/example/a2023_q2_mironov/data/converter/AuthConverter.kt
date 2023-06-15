package com.example.a2023_q2_mironov.data.converter

import com.example.a2023_q2_mironov.data.network.model.AuthDto
import com.example.a2023_q2_mironov.domain.entity.Auth
import javax.inject.Inject

class AuthConverter @Inject constructor() {

    fun convert(from: Auth): AuthDto =
        AuthDto(name = from.name, password = from.password)

    fun revert(from: AuthDto): Auth =
        Auth(name = from.name, password = from.password)
}