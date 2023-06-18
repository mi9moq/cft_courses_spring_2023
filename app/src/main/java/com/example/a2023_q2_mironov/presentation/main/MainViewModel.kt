package com.example.a2023_q2_mironov.presentation.main

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.navigation.router.MainRouter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: MainRouter
) : ViewModel() {

    fun showHistory() {
        router.openHistory()
    }

    fun showGuid() {
        //TODO(router.openGuid())
    }

    fun creteLoan() {
        //TODO(router.openCreate())
    }
}