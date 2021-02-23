package com.fakdl.moovies.di

import com.fakdl.moovies.di.auth.AuthComponent
import com.fakdl.moovies.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class,
        AuthComponent::class
    ]
)
class SubComponentsModule
