package com.fakdl.moovies.repository.db


import com.fakdl.moovies.models.User
import javax.inject.Inject


class UserRepository @Inject constructor(userDao: UserDatabaseDao) {
    private val userDao = userDao

    suspend fun isValidAcc(email: String, hashPassword: String): Boolean {
        val user = userDao.getAccountByEmail(email)
        return user?.hashPassword == hashPassword
    }
    suspend fun clearAll() = userDao.clear()
    suspend fun getAll() = userDao.getAll()
    suspend fun getAccByEmail(email: String): User? = userDao.getAccountByEmail(email)
    suspend fun getAccountByUsername(username: String): User? = userDao.getAccount(username)
    suspend fun createUser(email: String, username: String, hashPassword: String) {
        userDao.insert(User(username = username, email = email, hashPassword = hashPassword))
    }

}