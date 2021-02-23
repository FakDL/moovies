package com.fakdl.moovies.di.main
import com.fakdl.moovies.ui.main.MainActivity

import dagger.Subcomponent

@Subcomponent(modules = [
    MainModule::class,
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
