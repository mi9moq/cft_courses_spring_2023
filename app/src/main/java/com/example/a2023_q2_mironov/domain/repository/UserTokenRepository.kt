package com.example.a2023_q2_mironov.domain.repository

import com.example.a2023_q2_mironov.domain.entity.AccessUserToken

interface UserTokenRepository {

    fun getUserToken(): AccessUserToken

    fun setUserToken(token: AccessUserToken)

    fun resetUserToken()
}