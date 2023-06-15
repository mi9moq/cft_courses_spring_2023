package com.example.a2023_q2_mironov.domain.repository

import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User

interface AuthRepository {

    suspend fun registration(auth: Auth): User

    suspend fun login(auth: Auth): String
}