package com.moovies.data.network

import com.moovies.data.di.main.MainScope
import com.moovies.data.network.responses.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Query

@MainScope
interface FilmService {

    @GET("title/get-most-popular-movies")
    suspend fun getPopularFilmIdList(): List<String>

    @GET("title/get-details")
    suspend fun getFilm(@Query("tconst") id: String): FilmResponse

}