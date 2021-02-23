package com.fakdl.moovies.di.main

import android.app.Application
import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fakdl.moovies.R
import com.fakdl.moovies.fragments.main.MainFragmentFactory
import com.fakdl.moovies.repository.network.FilmRepository
import com.fakdl.moovies.repository.network.FilmService
import com.fakdl.moovies.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object MainModule {

    @MainScope
    @Provides
    fun provideFragmentFactory(
        sharedPreferences: SharedPreferences,
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return MainFragmentFactory(
            sharedPreferences,
            viewModelFactory
        )
    }

    @MainScope
    @Provides
    fun provideFilmService(retrofitBuilder: Retrofit.Builder): FilmService {
        return retrofitBuilder
            .build()
            .create(FilmService::class.java)
    }

    @MainScope
    @Provides
    fun provideFilmRepository(filmService: FilmService): FilmRepository {
        return FilmRepository(filmService)
    }

    @MainScope
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @MainScope
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.placeholder)
            .error(R.drawable.placeholder)
    }

    @MainScope
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }
}