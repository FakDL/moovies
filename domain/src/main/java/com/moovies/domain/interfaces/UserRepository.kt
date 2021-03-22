package com.moovies.domain.interfaces

import com.moovies.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String)
    suspend fun createUser(email: String, username: String, password: String)
    suspend fun getUser(): User
}