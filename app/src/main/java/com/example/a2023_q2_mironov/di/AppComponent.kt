package com.example.a2023_q2_mironov.di

import android.app.Application
import com.example.a2023_q2_mironov.LoanApp
import com.example.a2023_q2_mironov.di.module.AuthModule
import com.example.a2023_q2_mironov.di.module.DispatcherModule
import com.example.a2023_q2_mironov.di.module.LoanModule
import com.example.a2023_q2_mironov.di.module.NavigationModule
import com.example.a2023_q2_mironov.di.module.NetworkModule
import com.example.a2023_q2_mironov.di.module.UserModule
import com.example.a2023_q2_mironov.di.module.ViewModelModule
import com.example.a2023_q2_mironov.ui.HistoryFragment
import com.example.a2023_q2_mironov.ui.LoginFragment
import com.example.a2023_q2_mironov.ui.MainActivity
import com.example.a2023_q2_mironov.ui.MainFragment
import com.example.a2023_q2_mironov.ui.RegistrationFragment
import com.example.a2023_q2_mironov.ui.WelcomeFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DispatcherModule::class,
        AuthModule::class,
        NetworkModule::class,
        UserModule::class,
        LoanModule::class,
        ViewModelModule::class,
        NavigationModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(application: LoanApp)

    fun inject(fragment: WelcomeFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegistrationFragment)

    fun inject(fragment: MainFragment)

    fun inject(fragment: HistoryFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}