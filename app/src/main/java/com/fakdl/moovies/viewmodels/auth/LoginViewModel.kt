package com.fakdl.moovies.viewmodels.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakdl.moovies.repository.db.UserRepository
import com.fakdl.moovies.util.Constants.WRONG_ACC
import com.fakdl.moovies.viewmodels.SingleLiveEvent
import kotlinx.coroutines.async
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val _viewState = MutableLiveData<LoginViewState>(LoginViewState.Success)
    val viewState : LiveData<LoginViewState> get() = _viewState

    private val _actionState = SingleLiveEvent<LoginActionState>()
    val actionState : SingleLiveEvent<LoginActionState> get() = _actionState

    suspend fun clearAll() = userRepository.clearAll()
    suspend fun getAll() = userRepository.getAll()

    suspend fun login(username: String, hashPassword: String){
        _viewState.postValue(LoginViewState.Loading)
        val def = viewModelScope.async {
            return@async isAccExist(username, hashPassword)
        }

        when {
            def.await() -> {
                _viewState.postValue(LoginViewState.Success)
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                _actionState.postValue(LoginActionState.NeedsLogin)
            }
            else -> {
                _viewState.postValue(LoginViewState.LoginFailed(WRONG_ACC))

            }
        }
    }

    fun goToRegistration() {
        _actionState.value = LoginActionState.NeedsRegistration
    }

    private suspend fun isAccExist(username: String, hashPassword: String)=
        userRepository.isValidAcc(username, hashPassword)

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