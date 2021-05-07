package com.moovies.presentation.viewmodels.auth

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovies.data.util.Constants
import com.moovies.presentation.viewmodels.auth.RegistrationActionState.*
import com.moovies.domain.interactors.UserInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val userInteractor: UserInteractor
) : ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val _viewState = MutableLiveData<RegistrationViewState>(RegistrationViewState.Success)
    val viewState : LiveData<RegistrationViewState> get() = _viewState

    private val _actionState = MutableLiveData<RegistrationActionState>()
    val actionState : LiveData<RegistrationActionState> get() = _actionState

    fun register(email: String, username: String, hashPassword: String, confirmHashPassword: String) {
        _viewState.postValue(RegistrationViewState.Loading)
        when {
            email.isEmpty()|| username.isEmpty() || hashPassword.isEmpty() || confirmHashPassword.isEmpty() -> {
                _viewState.postValue(RegistrationViewState.RegistrationFailed(Constants.EMPTY_FIELDS))
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _viewState.postValue(RegistrationViewState.RegistrationFailed(Constants.WRONG_EMAIL))
            }
            hashPassword != confirmHashPassword -> {
                _viewState.postValue(RegistrationViewState.RegistrationFailed(Constants.DIFFERENT_PASSWORDS))
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _viewState.postValue(RegistrationViewState.Success)
                    userInteractor.createUser(email, username, hashPassword)
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    _actionState.postValue(NeedsLogin)
                }
            }
        }

    }

}

sealed class RegistrationViewState() {
    object Loading : RegistrationViewState()
    data class RegistrationFailed(val errorType: Int) : RegistrationViewState()
    object Success : RegistrationViewState()
}

sealed class RegistrationActionState {
    object NeedsLogin: RegistrationActionState()
}
