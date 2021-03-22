package com.moovies.presentation.viewmodels.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moovies.domain.FilmInteractor
import com.moovies.domain.model.Film
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsViewModel @Inject constructor (
    private val interactor: FilmInteractor
): ViewModel() {
    private val disposable = CompositeDisposable()

    private var _viewState = MutableLiveData<ListFetchingViewState>()
    val viewState : LiveData<ListFetchingViewState> get() = _viewState

    private var currentFilms: Flow<PagingData<Film>>? = null

    fun fetchData(): Flow<PagingData<Film>> {
        val newResult: Flow<PagingData<Film>> = interactor.getFilmStream()
            .cachedIn(viewModelScope)
        currentFilms = newResult
        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
sealed class ListFetchingViewState {
    object Loading: ListFetchingViewState()
    data class FetchingFailed(val errorType: Int): ListFetchingViewState()
    object Success: ListFetchingViewState()
}