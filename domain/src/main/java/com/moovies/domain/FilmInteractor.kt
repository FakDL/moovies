package com.moovies.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moovies.domain.Consts.NETWORK_PAGE_SIZE
import com.moovies.domain.interfaces.FilmRepository
import com.moovies.domain.model.Film
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class FilmInteractor(
    private val filmRepository: FilmRepository
) {
    suspend fun getFilms(startPoint: Int, size: Int): List<Film> {
        val list = mutableListOf<Film>()
        lateinit var def: Deferred<Boolean>
        filmRepository.getPopularFilmsId()
            .slice(IntRange(startPoint, startPoint + size))
            .forEach {
                def = GlobalScope.async {  list.add(filmRepository.getFilm(it.substring(7)))
            }
        }
        def.await()
        return list
    }

    fun getFilmStream(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { FilmPagingSource(this) }
        ).flow
    }
}