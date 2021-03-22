package com.moovies.presentation.di.main
import com.moovies.data.di.main.MainInterModule
import com.moovies.data.di.main.MainScope
import com.moovies.data.di.main.NetworkModule
import com.moovies.presentation.ui.main.MainActivity

import dagger.Subcomponent

@Subcomponent(modules = [
    MainModule::class,
    NetworkModule::class,
    MainInterModule::class,
    MainViewModelModule::class
])
@MainScope
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}
