package com.moovies.domain.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moovies.domain.Consts.NETWORK_PAGE_SIZE
import com.moovies.domain.interfaces.FilmRepository
import com.moovies.domain.interfaces.UserRepository
import com.moovies.domain.model.Film
import com.moovies.domain.pagingsources.FilmPagingSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class FilmInteractor @Inject constructor(
    private val filmRepository: FilmRepository,
    private val userRepository: UserRepository
) {

    var idList: List<String>? = null

    fun observeIsLoading(source: FilmPagingSource): Flow<Boolean> = source.isLoading.receiveAsFlow()

    suspend fun getFilmsByIdList(startPoint: Int, size: Int, ids: List<String>): List<Film> {
        val list = mutableListOf<Film>()
        val defs = ArrayList<Deferred<Boolean>>()
        var requestAmount = 0
        var firstTime: Long = 0
        var lastTime: Long = 0
        ids.slice(IntRange(startPoint, getEndpoint(ids, startPoint, size)))
            .forEach {
                if (requestAmount < 4) {
                    if (requestAmount == 0) firstTime = System.currentTimeMillis()
                    if (requestAmount == 3) lastTime = System.currentTimeMillis()
                    defs.add(GlobalScope.async {
                        list.add(filmRepository.getFilm(it))
                    })
                    requestAmount++
                } else {
                    delay(1000 - (lastTime - firstTime))
                    requestAmount = 0
                }
            }
        defs.awaitAll()
        return list
    }

    private fun getEndpoint(list: List<String>, startPoint: Int, size: Int): Int =
        if (list.size > startPoint + size) startPoint + size else list.size - 1


    suspend fun getFavoriteFilms(startPoint: Int, size: Int): List<Film> =
        getFilmsByIdList(startPoint, size, userRepository.getFavoriteFilmsId())

    suspend fun getPopularFilms(startPoint: Int, size: Int): List<Film> =
        getFilmsByIdList(startPoint, size, getPopularFilmsId())

    private suspend fun getPopularFilmsId(): List<String> {
        if (idList == null) idList = filmRepository.getPopularFilmsId()
        return (idList as List<String>).map { it.substring(7) }
    }

    suspend fun isFilmSaved(id: String) = userRepository.isFilmFav(id)

    suspend fun getFilmsByName(name: String): List<Film> = filmRepository.findByName(name)

    suspend fun getFilm(id: String) = filmRepository.getFilm(id)

    suspend fun getFilmRatings(id: String) = filmRepository.getRating(id)

    @FlowPreview
    fun getFilmStream(source: FilmPagingSource): Flow<PagingData<Film>> {
        println("TTT $source")
        source
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { source }
        ).flow
    }

    suspend fun likeFilm(filmId: String) {
        userRepository.likeFilm(filmId)
    }

    suspend fun deleteFromFav(filmId: String) {
        userRepository.deleteFromFav(filmId)
    }

}

