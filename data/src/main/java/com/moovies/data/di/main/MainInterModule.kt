package com.moovies.data.di.main

import com.moovies.data.network.FilmRepositoryImpl
import com.moovies.data.network.FilmService
import com.moovies.domain.FilmInteractor
import com.moovies.domain.interfaces.FilmRepository
import dagger.Module
import dagger.Provides

@Module
object MainInterModule {
    @MainScope
    @Provides
    fun provideFilmInteractor(
        filmRepository: FilmRepository
    ): FilmInteractor {
        return FilmInteractor(filmRepository)
    }
    @MainScope
    @Provides
    fun provideFilmRepository(
        filmService: FilmService
    ): FilmRepository {
        return FilmRepositoryImpl(filmService)
    }
}