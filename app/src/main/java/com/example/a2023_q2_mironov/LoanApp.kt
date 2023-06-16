package com.example.a2023_q2_mironov

import android.app.Application
import com.example.a2023_q2_mironov.di.DaggerAppComponent

class LoanApp: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}