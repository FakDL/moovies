package com.moovies.data.di.main

import com.moovies.data.BuildConfig
import com.moovies.data.network.FilmService
import com.moovies.data.util.Constants
import com.moovies.domain.di.MainScope
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @MainScope
    @Provides
    fun provideFilmService(retrofitBuilder: Retrofit.Builder): FilmService {
        return retrofitBuilder
            .build()
            .create(FilmService::class.java)
    }

    @MainScope
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = 5
        return OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", BuildConfig.API_KEY)
                .addHeader("x-rapidapi-host", "imdb8.p.rapidapi.com")
                .addHeader("useQueryString", true.toString())
                .build()
            chain.proceed(request)
        }.build()
    }

    @MainScope
    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }
}