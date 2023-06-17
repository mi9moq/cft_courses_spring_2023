package com.example.a2023_q2_mironov.data.datasource

import com.example.a2023_q2_mironov.data.converter.AuthConverter
import com.example.a2023_q2_mironov.data.converter.UserConverter
import com.example.a2023_q2_mironov.data.network.LoanApi
import com.example.a2023_q2_mironov.di.IoDispatcher
import com.example.a2023_q2_mironov.domain.entity.Auth
import com.example.a2023_q2_mironov.domain.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRemoteDataSource {

    suspend fun registration(auth: Auth): User

    suspend fun login(auth: Auth): String
}

class AuthRemoteDataSourceImpl @Inject constructor(
    private val loanApi: LoanApi,
    private val userConverter: UserConverter,
    private val authConverter: AuthConverter,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRemoteDataSource {
    override suspend fun registration(auth: Auth): User = withContext(ioDispatcher) {
        val response = loanApi.registration(authConverter.convert(auth))
        userConverter.revert(response)
    }

    override suspend fun login(auth: Auth): String = withContext(ioDispatcher) {
        val response = loanApi.login(authConverter.convert(auth))
        response.body()!!.string()
    }
}