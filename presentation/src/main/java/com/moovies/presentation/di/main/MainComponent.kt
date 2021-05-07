package com.moovies.presentation.di.main

import com.moovies.data.di.main.MainRepoModule
import com.moovies.data.di.main.NetworkModule
import com.moovies.domain.di.MainScope
import com.moovies.domain.di.PagingModule
import com.moovies.domain.pagingsources.FilmPagingSource
import com.moovies.presentation.ui.main.MainActivity
import dagger.BindsInstance

import dagger.Subcomponent

@Subcomponent(
    modules = [
        MainModule::class,
        NetworkModule::class,
        MainRepoModule::class,
        PagingModule::class,
        MainViewModelModule::class
    ]
)
@MainScope
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}
