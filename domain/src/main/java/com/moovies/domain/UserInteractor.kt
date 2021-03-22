package com.moovies.domain

import com.moovies.domain.interfaces.UserRepository

class UserInteractor (
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