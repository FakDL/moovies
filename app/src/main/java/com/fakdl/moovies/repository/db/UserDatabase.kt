package com.fakdl.moovies.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fakdl.moovies.models.Film
import com.fakdl.moovies.models.User

@Database(entities = [User::class, Film::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDatabaseDao: UserDatabaseDao

    companion object {
        val DATABASE_NAME: String = "app_db"
    }
}