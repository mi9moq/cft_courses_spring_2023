package com.example.a2023_q2_mironov.data.datasource

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.example.a2023_q2_mironov.domain.entity.AccessUserToken
import javax.inject.Inject

interface TokenLocalDataSource {

    fun get(): AccessUserToken

    fun set(token: AccessUserToken)

    fun reset()
}

class TokenLocalDataSourceImpl @Inject constructor(
    private val application: Application
) : TokenLocalDataSource {

    companion object {
        private const val APP_PREFERENCES = "settings"
        private const val PREFERENCE_NAME_USER_TOKEN = "UserToken"
        private const val DEFAULT_VALUE = ""
    }

    override fun get(): AccessUserToken {
        val token = application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        ).getString(PREFERENCE_NAME_USER_TOKEN, DEFAULT_VALUE)
        return AccessUserToken(token ?: DEFAULT_VALUE)
    }

    override fun set(token: AccessUserToken) {
        application.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
            .edit()
            .putString(PREFERENCE_NAME_USER_TOKEN, token.userToken)
            .apply()
    }

    override fun reset() {
        application.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
            .edit()
            .remove(PREFERENCE_NAME_USER_TOKEN)
            .apply()
    }
}