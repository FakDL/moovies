package com.moovies.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moovies.domain.Consts.FILM_STARTING_PAGE_INDEX
import com.moovies.domain.model.Film
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.IOException

class FilmPagingSource (
    private val interactor: FilmInteractor
):PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val position = params.key ?: FILM_STARTING_PAGE_INDEX
        return try {
            val films = GlobalScope.async{
                interactor.getFilms(position, params.loadSize*2/3-1)
            }.await()
            val nextKey = if (films.isEmpty()) {
                null
            } else {
                position + params.loadSize*2/3 + 1
            }
            LoadResult.Page(
                data = films,
                prevKey = if (position == FILM_STARTING_PAGE_INDEX) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

}