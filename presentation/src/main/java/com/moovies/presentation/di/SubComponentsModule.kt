package com.moovies.presentation.di

import com.moovies.presentation.di.auth.AuthComponent
import com.moovies.presentation.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class,
        AuthComponent::class
    ]
)
class SubComponentsModule
