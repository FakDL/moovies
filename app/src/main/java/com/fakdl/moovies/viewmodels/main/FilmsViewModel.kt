package com.fakdl.moovies.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakdl.moovies.repository.network.FilmRepository
import com.fakdl.moovies.repository.network.responses.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmsViewModel @Inject constructor(): ViewModel() {

    private val _viewState = MutableLiveData<FetchingViewState>()
    val viewState : LiveData<FetchingViewState> get() = _viewState

    @Inject
    lateinit var filmRepository: FilmRepository

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = filmRepository.getPopularFilms()

                if (!response.equals(null)) {
                    _viewState.postValue(FetchingViewState.Success(response.results))
                }
            }catch (e: Exception){
                _viewState.postValue(FetchingViewState.FetchingFailed(1))
            }
        }
    }
}
sealed class FetchingViewState {
    object Loading: FetchingViewState()
    data class FetchingFailed(val errorType: Int): FetchingViewState()
    data class Success(val films: List<Result> ): FetchingViewState()
}