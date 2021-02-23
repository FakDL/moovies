package com.fakdl.moovies.di.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakdl.moovies.di.ViewModelKey
import com.fakdl.moovies.viewmodels.AuthViewModelFactory
import com.fakdl.moovies.viewmodels.auth.LoginViewModel
import com.fakdl.moovies.viewmodels.auth.RegistrationViewModel
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