package com.example.a2023_q2_mironov.utils

import com.example.a2023_q2_mironov.data.network.model.UserDto
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User

object AuthData {

    val auth = Auth(name = "User Name", password = "Password")

    val userEntity = User(name = "User Name", role = "USER")

    val userDto = UserDto(name = "User Name", role = "USER")

    const val token = "eqweqwasdaweq"
}