package com.moovies.domain.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moovies.domain.Consts
import com.moovies.domain.Consts.FRAGMENT_FILMS_ID
import com.moovies.domain.Consts.FRAGMENT_LIKES_ID
import com.moovies.domain.interactors.FilmInteractor
import com.moovies.domain.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import java.io.IOException

class FilmPagingSource(
    private val interactor: FilmInteractor,
    private val fragmentId: Int
) : PagingSource<Int, Film>() {

    val isLoading = Channel<Boolean>()
    private var isInitialLoading = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        if (isInitialLoading) isLoading.send(true)

        val position = params.key ?: Consts.FILM_STARTING_PAGE_INDEX
        return try {
            val films = withContext(Dispatchers.Default) {
                getList(fragmentId, position, params.loadSize * 2 / 3 - 1)
            }
            println("load data")
            val nextKey = if (films.isEmpty()) {
                null
            } else {
                position + params.loadSize * 2 / 3 + 1
            }

            isLoading.send(false)
            isInitialLoading = false

            LoadResult.Page(
                data = films,
                prevKey = if (position == Consts.FILM_STARTING_PAGE_INDEX) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

    private suspend fun getList(fragmentId: Int, position: Int, size: Int): List<Film> {
        val list: List<Film> = when (fragmentId) {
            FRAGMENT_FILMS_ID -> {
                withContext(Dispatchers.Default) { interactor.getPopularFilms(position, size) }
            }
            FRAGMENT_LIKES_ID -> {
                withContext(Dispatchers.Default) { interactor.getFavoriteFilms(position, size) }
            }
            else -> {
                listOf()
            }
        }
        return list
    }

}