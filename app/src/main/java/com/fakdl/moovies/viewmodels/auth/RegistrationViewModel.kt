package com.fakdl.moovies.viewmodels.auth

import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakdl.moovies.repository.db.UserRepository
import com.fakdl.moovies.util.Constants
import com.fakdl.moovies.viewmodels.auth.RegistrationActionState.*
import kotlinx.coroutines.async
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val _viewState = MutableLiveData<RegistrationViewState>(RegistrationViewState.Success)
    val viewState : LiveData<RegistrationViewState> get() = _viewState

    private val _actionState = MutableLiveData<RegistrationActionState>()
    val actionState : LiveData<RegistrationActionState> get() = _actionState

    private val userRepository = userRepository

    suspend fun register(email: String, username: String, hashPassword: String, confirmHashPassword: String) {
        _viewState.postValue(RegistrationViewState.Loading)
        val def = viewModelScope.async {
            return@async isEmailAvailable(email)
        }
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

            def.await()-> {
                _viewState.postValue(RegistrationViewState.RegistrationFailed(Constants.WRONG_EMAIL))
            }

            else -> {
                _viewState.postValue(RegistrationViewState.Success)
                userRepository.createUser(email, username, hashPassword)
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                _actionState.postValue(NeedsLogin)
            }
        }

    }
    private suspend fun isEmailAvailable(email: String): Boolean {
        val user = userRepository.getAccByEmail(email = email)
        return user != null
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
