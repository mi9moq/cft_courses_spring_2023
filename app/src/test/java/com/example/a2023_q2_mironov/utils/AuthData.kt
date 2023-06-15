package com.example.a2023_q2_mironov.utils

import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User

object AuthData {

    val auth = Auth(name = "User Name", password = "Password")

    val user = User(name = "User Name", role = "USER")

    val token = "eqweqwasdaweq"
}