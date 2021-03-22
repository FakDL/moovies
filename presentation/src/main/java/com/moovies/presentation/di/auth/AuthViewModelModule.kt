package com.moovies.presentation.di.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moovies.presentation.di.ViewModelKey
import com.moovies.presentation.viewmodels.AuthViewModelFactory
import com.moovies.presentation.viewmodels.auth.LoginViewModel
import com.moovies.presentation.viewmodels.auth.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @AuthScope
    @Binds
    abstract fun bindViewModelFactory(factory: AuthViewModelFactory): ViewModelProvider.Factory

    @AuthScope
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @AuthScope
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel
}