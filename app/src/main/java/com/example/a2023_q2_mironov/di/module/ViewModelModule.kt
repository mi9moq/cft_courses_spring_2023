package com.example.a2023_q2_mironov.di.module

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.di.ViewModelKey
import com.example.a2023_q2_mironov.presentation.create.CreateLoanViewModel
import com.example.a2023_q2_mironov.presentation.details.DetailsViewModel
import com.example.a2023_q2_mironov.presentation.guid.GuidViewModel
import com.example.a2023_q2_mironov.presentation.history.HistoryViewModel
import com.example.a2023_q2_mironov.presentation.login.LoginViewModel
import com.example.a2023_q2_mironov.presentation.main.MainViewModel
import com.example.a2023_q2_mironov.presentation.registration.RegistrationViewModel
import com.example.a2023_q2_mironov.presentation.welcom.WelcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(impl: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(impl: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(impl: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryViewModel(impl: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(impl: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateLoanViewModel::class)
    fun bindCreateLoanViewModel(impl: CreateLoanViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GuidViewModel::class)
    fun bindGuidViewModel(impl: GuidViewModel): ViewModel
}