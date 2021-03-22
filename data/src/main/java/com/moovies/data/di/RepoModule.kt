package com.moovies.data.di

import com.moovies.data.db.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.moovies.domain.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepoModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase
    ): UserRepository {
        return UserRepositoryImpl(auth, database)
    }
}