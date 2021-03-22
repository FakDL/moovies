package com.moovies.presentation.viewmodels.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovies.data.util.Constants.WRONG_ACC
import com.moovies.presentation.viewmodels.SingleLiveEvent
import com.moovies.domain.UserInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val sharedPreferences: SharedPreferences
    ): ViewModel() {


    private val _viewState = MutableLiveData<LoginViewState>(LoginViewState.Success)
    val viewState : LiveData<LoginViewState> get() = _viewState

    private val _actionState = SingleLiveEvent<LoginActionState>()
    val actionState : SingleLiveEvent<LoginActionState> get() = _actionState

    suspend fun login(email: String, hashPassword: String){

        _viewState.postValue(LoginViewState.Loading)

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _viewState.postValue(LoginViewState.LoginFailed(WRONG_ACC))
            _viewState.postValue(LoginViewState.Success)
        }

        viewModelScope.launch(errorHandler) {
            userInteractor.login(email, hashPassword)
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            _actionState.postValue(LoginActionState.NeedsLogin)
        }
    }

    fun goToRegistration() {
        _actionState.value = LoginActionState.NeedsRegistration
    }

}
sealed class LoginViewState {
    object Loading: LoginViewState()
    data class LoginFailed(val errorType: Int): LoginViewState()
    object Success: LoginViewState()
}
sealed class LoginActionState {
    object NeedsRegistration : LoginActionState()
    object NeedsLogin: LoginActionState()
}