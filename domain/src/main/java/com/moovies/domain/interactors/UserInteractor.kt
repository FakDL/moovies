package com.moovies.domain.interactors

import com.moovies.domain.interfaces.UserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun createUser(email: String, username: String, hashPassword: String) {
        userRepository.createUser(email,username,hashPassword)
    }

    suspend fun login(email: String, hashPassword: String) {
        userRepository.login(email, hashPassword)
    }

    suspend fun getUser() = userRepository.getUser()


}