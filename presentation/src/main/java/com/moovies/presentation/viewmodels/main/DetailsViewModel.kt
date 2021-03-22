package com.moovies.presentation.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovies.data.network.FilmRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val filmRepository: FilmRepositoryImpl
): ViewModel() {

    private val _viewState = MutableLiveData<DetailsFetchingViewState>()
    val viewState : LiveData<DetailsFetchingViewState> get() = _viewState

    fun setFilmDetails(id: Long) {
        viewModelScope.launch {
//            val film = filmRepository.getFilmById(id)
//            _viewState.postValue(DetailsFetchingViewState.Success(film))
        }
    }

}
sealed class DetailsFetchingViewState {
    object Loading: DetailsFetchingViewState()
    data class FetchingFailed(val errorType: Int): DetailsFetchingViewState()
    data class Success(val film: String): DetailsFetchingViewState()
}