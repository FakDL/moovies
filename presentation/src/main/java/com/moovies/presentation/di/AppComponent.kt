package com.moovies.presentation.di

import android.app.Application
import com.moovies.data.di.AppModule
import com.moovies.data.di.DBModule
import com.moovies.data.di.RepoModule
import com.moovies.presentation.di.auth.AuthComponent
import com.moovies.presentation.di.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DBModule::class,
    RepoModule::class,
    SubComponentsModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun authComponent(): AuthComponent.Factory
}