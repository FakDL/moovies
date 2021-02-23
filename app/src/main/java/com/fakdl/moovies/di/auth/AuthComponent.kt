package com.fakdl.moovies.di.auth

import com.fakdl.moovies.ui.auth.AuthActivity

import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [
    AuthModule::class,
    AuthViewModelModule::class
])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): AuthComponent
    }

    fun inject(activity: AuthActivity)
}