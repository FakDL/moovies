package com.fakdl.moovies.repository.network

import com.fakdl.moovies.di.main.MainScope
import com.fakdl.moovies.repository.network.responses.FilmsResponse
import com.fakdl.moovies.repository.network.responses.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@MainScope
interface FilmService {

    @GET("movie/{id}")
    suspend fun getFilm(@Path("id") filmId: Long, @Query("api_key") apiKey: String): Result

    @GET("movie/popular")
    suspend fun getPopularFilms(@Query("api_key") apiKey: String): FilmsResponse
}