package com.moovies.domain.di

import com.moovies.domain.Consts.FRAGMENT_FILMS_ID
import com.moovies.domain.Consts.FRAGMENT_LIKES_ID
import com.moovies.domain.interactors.FilmInteractor
import com.moovies.domain.pagingsources.FilmPagingSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
object PagingModule {
    @Provides
    @MainScope
    @Named("favPagingSource")
    fun provideFavPagingSource(interactor: FilmInteractor): FilmPagingSource =
        FilmPagingSource(interactor, FRAGMENT_LIKES_ID)
    @Provides
    @MainScope
    @Named("popularPagingSource")
    fun providePopularPagingSource(interactor: FilmInteractor): FilmPagingSource =
        FilmPagingSource(interactor, FRAGMENT_FILMS_ID)
}
