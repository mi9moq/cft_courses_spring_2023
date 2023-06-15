package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User
import com.example.a2023_q2_mironov.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun registration(auth: Auth): User = authRemoteDataSource.registration(auth)

    override suspend fun login(auth: Auth): String = authRemoteDataSource.login(auth)
}