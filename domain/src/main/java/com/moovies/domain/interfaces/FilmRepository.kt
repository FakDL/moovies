package com.moovies.domain.interfaces

import com.moovies.domain.model.Film

interface FilmRepository {
    suspend fun getPopularFilmsId(): List<String>
    suspend fun getFilm(id: String): Film
}