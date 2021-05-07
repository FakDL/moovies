package com.moovies.presentation.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moovies.domain.interactors.FilmInteractor
import com.moovies.domain.model.Film
import com.moovies.domain.pagingsources.FilmPagingSource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.subscribe
import javax.inject.Inject
import javax.inject.Named

class FilmsViewModel @Inject constructor(
    private val interactor: FilmInteractor
) : ViewModel() {

    @Inject
    @Named("popularPagingSource")
    lateinit var source: FilmPagingSource

    private var _viewState = MutableLiveData<ListFetchingViewState>()
    val viewState: LiveData<ListFetchingViewState> get() = _viewState

    private var currentFilms: Flow<PagingData<Film>>? = null

    @FlowPreview
    fun fetchData(): Flow<PagingData<Film>> {
        val newResult: Flow<PagingData<Film>> = interactor.getFilmStream(source)
            .cachedIn(viewModelScope)
        viewModelScope.launch { setupObservers() }
        currentFilms = newResult
        return newResult
    }

    suspend fun findFilms(name: String): List<Film> {
        var list: List<Film>
        withContext(Dispatchers.Default) {
            list = interactor.getFilmsByName(name)
        }
        return list
    }


    suspend fun setupObservers() {
        interactor.observeIsLoading(source).collectLatest { isLoading ->
            if (isLoading) {
                _viewState.value = ListFetchingViewState.Loading
            } else {
                _viewState.value = ListFetchingViewState.Success
            }
        }
    }

}

sealed class ListFetchingViewState {
    object Loading : ListFetchingViewState()
    data class FetchingFailed(val errorType: Int) : ListFetchingViewState()
    object Success : ListFetchingViewState()
}