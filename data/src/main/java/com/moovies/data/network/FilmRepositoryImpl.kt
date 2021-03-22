package com.moovies.data.network

import android.util.Log
import com.moovies.domain.interfaces.FilmRepository
import com.moovies.domain.model.Film
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val filmService: FilmService
): FilmRepository {

    override suspend fun getPopularFilmsId(): List<String> {
        val list = filmService.getPopularFilmIdList()
        Log.d("Test", list[0])
        return list
    }
    override suspend fun getFilm(id: String): Film {
        val film =  filmService.getFilm(id).toFilm()
        Log.d("Test", film.toString())
        return film
    }
}