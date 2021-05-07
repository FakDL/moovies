package com.moovies.presentation.viewmodels.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovies.domain.interactors.FilmInteractor
import com.moovies.domain.model.Film
import com.moovies.domain.model.FilmRatings
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val filmInteractor: FilmInteractor
): ViewModel() {

    private val _viewState = MutableLiveData<DetailsFetchingViewState>()
    val viewState : LiveData<DetailsFetchingViewState> get() = _viewState

    private val _isLiked = MutableLiveData<Boolean>()
    val isLiked : LiveData<Boolean> get() = _isLiked

    fun setFilmDetails(id: String) {
        _viewState.value = DetailsFetchingViewState.Loading

        viewModelScope.launch {
            _isLiked.postValue(filmInteractor.isFilmSaved(id))
            val film = async{filmInteractor.getFilm(id)}
            val ratings = async { filmInteractor.getFilmRatings(id) }
            _viewState.postValue(DetailsFetchingViewState.Success(film.await(), ratings.await()))
        }
    }

    suspend fun likeFilm(filmId: String) {
        filmInteractor.likeFilm(filmId)
        _isLiked.postValue(true)
    }

    suspend fun deleteFromFav(filmId: String) {
        filmInteractor.deleteFromFav(filmId)
        _isLiked.postValue(false)
    }

}
sealed class DetailsFetchingViewState {
    object Loading: DetailsFetchingViewState()
    data class FetchingFailed(val errorType: Int): DetailsFetchingViewState()
    data class Success(val film: Film, val ratings: FilmRatings): DetailsFetchingViewState()
}