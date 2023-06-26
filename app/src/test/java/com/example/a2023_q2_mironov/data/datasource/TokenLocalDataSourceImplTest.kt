package com.example.a2023_q2_mironov.data.datasource

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TokenLocalDataSourceImplTest {

    companion object {
        private const val APP_PREFERENCES = "settings"
        private const val PREFERENCE_NAME_USER_TOKEN = "UserToken"
        private const val DEFAULT_VALUE = ""
    }

    private val application: Application = mock()
    private val dataSource = TokenLocalDataSourceImpl(application)
    private val sharedPreferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val testToken = "user token"

    @BeforeEach
    fun setupSharedPreferences(){
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        )) doReturn sharedPreferences
    }

    @Test
    fun `preferences is empty EXPECT default value`(){
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
        ).getString(PREFERENCE_NAME_USER_TOKEN, DEFAULT_VALUE)) doReturn testToken

        val actual = dataSource.get().userToken
        val expected = testToken

        assertEquals(expected, actual)
    }

    @Test
    fun `reset EXPECT clean preferences`(){
        whenever(application.getSharedPreferences(
            APP_PREFERENCES,
            MODE_PRIVATE
        ).edit()) doReturn editor

        dataSource.reset()

        verify(editor).remove(PREFERENCE_NAME_USER_TOKEN)
        verify(editor).apply()
    }
}