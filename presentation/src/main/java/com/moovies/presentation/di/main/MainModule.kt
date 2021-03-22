package com.moovies.presentation.di.main

import android.app.Application
import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.moovies.data.di.main.MainScope
import com.moovies.presentation.R
import com.moovies.presentation.factories.main.MainFragmentFactory
import com.moovies.presentation.glide.GlideApp
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @MainScope
    @Provides
    fun provideFragmentFactory(
        sharedPreferences: SharedPreferences,
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return MainFragmentFactory(
            sharedPreferences,
            viewModelFactory,
            requestManager
        )
    }


    @MainScope
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.placeholder)
            .error(R.drawable.error)
    }

    @MainScope
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return GlideApp.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

}