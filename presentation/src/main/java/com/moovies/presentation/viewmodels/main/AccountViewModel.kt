package com.moovies.presentation.viewmodels.main

import androidx.lifecycle.ViewModel
import com.moovies.domain.model.User
import com.moovies.domain.UserInteractor
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {

//    private val _viewState = MutableLiveData<DetailsFetchingViewState>()
//    val viewState : LiveData<DetailsFetchingViewState> get() = _viewState

    suspend fun getUserData(): User = userInteractor.getUser()


}