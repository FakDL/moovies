package com.moovies.domain.interfaces

import com.moovies.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String)
    suspend fun createUser(email: String, username: String, password: String)
    suspend fun getUser(): User
    suspend fun likeFilm(id: String): Boolean
    suspend fun deleteFromFav(id: String): Boolean
    suspend fun isFilmFav(id: String): Boolean
    suspend fun getFavoriteFilmsId(): List<String>
}
