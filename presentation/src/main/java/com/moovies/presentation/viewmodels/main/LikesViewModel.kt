package com.moovies.presentation.viewmodels.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moovies.domain.Consts.FRAGMENT_LIKES_ID
import com.moovies.domain.interactors.FilmInteractor
import com.moovies.domain.model.Film
import com.moovies.domain.pagingsources.FilmPagingSource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class LikesViewModel @Inject constructor(
    private val interactor: FilmInteractor
) : ViewModel() {

    val source = FilmPagingSource(interactor, FRAGMENT_LIKES_ID)
    private var currentFilms: Flow<PagingData<Film>>? = null

    private var _viewState = MutableLiveData<ListFetchingViewState>()
    val viewState: LiveData<ListFetchingViewState> get() = _viewState

    @FlowPreview
    fun fetchData(): Flow<PagingData<Film>> {
        Log.d("Test", "$source in fetchData")
        val newResult: Flow<PagingData<Film>> = interactor.getFilmStream(source)
            .cachedIn(viewModelScope)
        viewModelScope.launch { setupObservers() }
        currentFilms = newResult
        return newResult
    }

    private suspend fun setupObservers() {
        Log.d("Test", "$source in setupObservers")
        interactor.observeIsLoading(source).collect { isLoading ->
            if (isLoading) {
                _viewState.value = ListFetchingViewState.Loading
            } else {
                _viewState.value = ListFetchingViewState.Success
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Test", "onCleared")
    }

}
