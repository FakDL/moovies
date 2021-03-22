package com.moovies.data.di

import android.app.Application
import android.content.SharedPreferences
import android.content.Context
import com.moovies.domain.UserInteractor
import com.moovies.domain.interfaces.UserRepository
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
    fun provideUserInteractor(
        userRepository: UserRepository
    ): UserInteractor {
        return UserInteractor(userRepository = userRepository)
    }

}
