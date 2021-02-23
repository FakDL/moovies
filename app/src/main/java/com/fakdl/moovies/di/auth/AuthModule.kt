package com.fakdl.moovies.di.auth

import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.fakdl.moovies.fragments.auth.AuthFragmentFactory
import dagger.Module
import dagger.Provides

@Module
object AuthModule {

    @AuthScope
    @Provides
    fun provideFragmentFactory(
        sharedPreferences: SharedPreferences,
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return AuthFragmentFactory(
            sharedPreferences,
            viewModelFactory
        )
    }
}