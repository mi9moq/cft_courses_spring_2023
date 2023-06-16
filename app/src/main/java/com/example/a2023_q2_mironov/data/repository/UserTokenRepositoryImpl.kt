package com.example.a2023_q2_mironov.data.repository

import com.example.a2023_q2_mironov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import com.example.a2023_q2_mironov.domain.repository.UserTokenRepository
import javax.inject.Inject

class UserTokenRepositoryImpl @Inject constructor(
    private val dataSource: TokenLocalDataSource
) : UserTokenRepository {

    override fun getUserToken(): AccessUserToken =
        dataSource.get()

    override fun setUserToken(token: AccessUserToken) =
        dataSource.set(token)
}