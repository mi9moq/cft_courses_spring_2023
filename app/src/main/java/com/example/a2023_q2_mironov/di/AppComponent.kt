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
import com.example.a2023_q2_mironov.ui.confirm.ConfirmLoanFragment
import com.example.a2023_q2_mironov.ui.create.CreateLoanFragment
import com.example.a2023_q2_mironov.ui.details.DetailsFragment
import com.example.a2023_q2_mironov.ui.guid.GuidFragment
import com.example.a2023_q2_mironov.ui.history.HistoryFragment
import com.example.a2023_q2_mironov.ui.approved.LoanApprovedFragment
import com.example.a2023_q2_mironov.ui.login.LoginFragment
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.ui.main.MainFragment
import com.example.a2023_q2_mironov.ui.regitration.RegistrationFragment
import com.example.a2023_q2_mironov.ui.welcome.WelcomeFragment
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

    fun inject(fragment: DetailsFragment)

    fun inject(fragment: CreateLoanFragment)

    fun inject(fragment: GuidFragment)

    fun inject(fragment: ConfirmLoanFragment)

    fun inject(fragment: LoanApprovedFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}