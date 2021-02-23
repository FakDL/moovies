package com.fakdl.moovies.repository.db

import com.fakdl.moovies.models.User
import com.fakdl.moovies.models.UserWithFilms
import androidx.room.*

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun clear()

    @Query("SELECT * FROM user_table ORDER BY email DESC")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user_table WHERE username LIKE :username")
    suspend fun getAccount(username: String): User?

    @Transaction
    @Query("SELECT * FROM user_table")
    suspend fun getUserWithFilms(): List<UserWithFilms>

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
    suspend fun getAccountByEmail(email: String): User?

}
