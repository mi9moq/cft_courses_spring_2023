package com.example.a2023_q2_mironov.data.datasource

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class TokenLocalDataSourceImplTest {

    companion object {
        private const val APP_PREFERENCES = "settings"
        private const val PREFERENCE_NAME_USER_TOKEN = "UserToken"
        private const val DEFAULT_VALUE = ""
    }

    private val application = mock(Application::class.java)
    private val dataSource = TokenLocalDataSourceImpl(application)

    private val testToken = "user token"

    @Test
    fun `preferences is empty EXPECT default value`(){
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        )) doReturn mock(SharedPreferences::class.java)
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        ).getString(PREFERENCE_NAME_USER_TOKEN, DEFAULT_VALUE)) doReturn DEFAULT_VALUE

        val actual = dataSource.get().userToken
        val expected = DEFAULT_VALUE

        assertEquals(expected, actual)
    }

    @Test
    fun `get EXPECT set token`(){
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        )) doReturn mock(SharedPreferences::class.java)
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        ).getString(PREFERENCE_NAME_USER_TOKEN, DEFAULT_VALUE)) doReturn testToken

        val actual = dataSource.get().userToken
        val expected = testToken

        assertEquals(expected, actual)
    }
    @Test
    fun `set EXPECT save token to preferences`(){
        // TODO проверить что значение сохраняется
    }
}