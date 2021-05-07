package com.moovies.data.network

import com.moovies.domain.model.FilmRatings
import com.moovies.data.network.responses.FilmResponse
import com.moovies.data.network.responses.FindResponse
import com.moovies.domain.di.MainScope
import retrofit2.http.GET
import retrofit2.http.Query

@MainScope
interface FilmService {

    @GET("title/get-most-popular-movies")
    suspend fun getPopularFilmIdList(): List<String>

    @GET("title/get-details")
    suspend fun getFilm(@Query("tconst") id: String): FilmResponse

    @GET("title/get-ratings")
    suspend fun getRatings(@Query("tconst") id: String): FilmRatings

    @GET("title/find")
    suspend fun getFilmsByName(@Query("q") name: String): FindResponse

}