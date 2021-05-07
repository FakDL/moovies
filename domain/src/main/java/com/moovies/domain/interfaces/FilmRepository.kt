package com.moovies.domain.interfaces

import com.moovies.domain.model.Film
import com.moovies.domain.model.FilmRatings

interface FilmRepository {
    suspend fun getPopularFilmsId(): List<String>
    suspend fun getFilm(id: String): Film
    suspend fun getRating(id: String): FilmRatings
    suspend fun findByName(name: String): List<Film>
}
