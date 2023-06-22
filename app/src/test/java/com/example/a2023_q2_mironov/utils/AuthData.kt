package com.example.a2023_q2_mironov.utils

import com.example.a2023_q2_mironov.data.network.model.AuthDto
import com.example.a2023_q2_mironov.data.network.model.UserDto
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User

object AuthData {

    val authEntity = Auth(name = "UserName", password = "Password")

    val authDto = AuthDto(name = "UserName", password = "Password")

    val userEntity = User(name = "UserName", role = "USER")

    val userDto = UserDto(name = "UserName", role = "USER")

    const val token = "eqweqwasdaweq"
}