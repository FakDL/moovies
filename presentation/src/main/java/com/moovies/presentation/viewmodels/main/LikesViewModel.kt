package com.moovies.presentation.viewmodels.main

import androidx.lifecycle.ViewModel
import com.moovies.data.network.FilmRepositoryImpl
import javax.inject.Inject

class LikesViewModel @Inject constructor(
    private val filmRepository: FilmRepositoryImpl
): ViewModel() {

}
