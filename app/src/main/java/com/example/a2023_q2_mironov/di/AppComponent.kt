package com.example.a2023_q2_mironov.di

import android.app.Application
import com.example.a2023_q2_mironov.LoanApp
import com.example.a2023_q2_mironov.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [
    DataModule::class,
    DispatcherModule::class
])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(application: LoanApp)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}