package com.example.a2023_q2_mironov.di.module

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.di.ViewModelKey
import com.example.a2023_q2_mironov.presentation.login.LoginViewModel
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
}