package com.fakdl.moovies.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakdl.moovies.di.ViewModelKey
import com.fakdl.moovies.viewmodels.MainViewModelFactory
import com.fakdl.moovies.viewmodels.main.FilmsViewModel
import com.fakdl.moovies.viewmodels.main.LikesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @MainScope
    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(FilmsViewModel::class)
    abstract fun bindFilmsViewModel(viewModel: FilmsViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(LikesViewModel::class)
    abstract fun bindLikesViewModel(viewModel: LikesViewModel): ViewModel
}
