package com.fakdl.moovies.repository.network

import com.fakdl.moovies.repository.network.responses.FilmsResponse
import com.fakdl.moovies.repository.network.responses.Result
import com.fakdl.moovies.util.Constants.API_KEY
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val filmService: FilmService
) {

    suspend fun getFilmById(id: Long): Result {
        return filmService.getFilm(id, API_KEY)
    }

    suspend fun getPopularFilms(): FilmsResponse = filmService.getPopularFilms(API_KEY)


}