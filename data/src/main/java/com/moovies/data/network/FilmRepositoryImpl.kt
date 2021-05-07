package com.moovies.data.network

import android.util.Log
import com.moovies.data.network.responses.FindResponse
import com.moovies.domain.interfaces.FilmRepository
import com.moovies.domain.model.Film
import com.moovies.domain.model.FilmRatings
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val filmService: FilmService
) : FilmRepository {

    override suspend fun getPopularFilmsId(): List<String> {
        val list = filmService.getPopularFilmIdList()
        Log.d("Test", list[0])
        return list
    }

    override suspend fun getFilm(id: String): Film = filmService.getFilm(id).toFilm()

    override suspend fun getRating(id: String): FilmRatings = filmService.getRatings(id)

    override suspend fun findByName(name: String): List<Film> {
        val films = mutableListOf<Film>()
        val list = filmService.getFilmsByName(name).results
        list.forEach {
            if (it.id.substring(1, 6) == "title" && it.titleType == "movie") {
                Log.d("Test", it.id.substring(1, 6) + it.titleType)
                films.add(it.toFilm())
            }
        }
        return films
    }
}