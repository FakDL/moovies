package com.moovies.presentation.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moovies.domain.interactors.UserInteractor
import com.moovies.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {

    private val _viewState = MutableLiveData<AccountViewState>()
    val viewState: LiveData<AccountViewState> get() = _viewState

    suspend fun getUserData() {
        _viewState.value = AccountViewState.Loading
        val user =
            withContext(Dispatchers.Default) { userInteractor.getUser() }
        _viewState.postValue(AccountViewState.Success(user))

    }


}

sealed class AccountViewState {
    object Loading : AccountViewState()
    data class FetchingFailed(val errorType: Int) : AccountViewState()
    data class Success(val user: User) : AccountViewState()
}