package com.moovies.presentation.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moovies.domain.di.MainScope
import com.moovies.presentation.di.ViewModelKey
import com.moovies.presentation.viewmodels.MainViewModelFactory
import com.moovies.presentation.viewmodels.main.AccountViewModel
import com.moovies.presentation.viewmodels.main.DetailsViewModel
import com.moovies.presentation.viewmodels.main.FilmsViewModel
import com.moovies.presentation.viewmodels.main.LikesViewModel
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
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

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

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel
}
