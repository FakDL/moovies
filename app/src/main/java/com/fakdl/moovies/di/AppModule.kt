package com.fakdl.moovies.di

import android.app.Application
import android.content.SharedPreferences
import android.content.Context
import androidx.room.Room
import com.fakdl.moovies.repository.db.UserDatabase
import com.fakdl.moovies.repository.db.UserDatabase.Companion.DATABASE_NAME
import com.fakdl.moovies.repository.db.UserDatabaseDao
import com.fakdl.moovies.repository.db.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object AppModule  {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences {
        return application
            .getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            )
    }

    @Singleton
    @Provides
    fun provideSharedPrefsEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideAppDb(app: Application): UserDatabase {
        return Room
            .databaseBuilder(app, UserDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDatabaseDao: UserDatabaseDao): UserRepository {
        return UserRepository(userDatabaseDao)
    }

    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDatabaseDao {
        return userDatabase.userDatabaseDao
    }

}
