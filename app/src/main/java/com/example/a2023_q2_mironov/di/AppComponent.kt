package com.example.a2023_q2_mironov.di

import com.example.a2023_q2_mironov.MainActivity
import dagger.Component

@AppScope
@Component(modules = [
    DataModule::class,
    DispatcherModule::class
])
interface AppComponent {

    fun inject(activity: MainActivity)
}