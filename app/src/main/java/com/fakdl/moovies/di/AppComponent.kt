package com.fakdl.moovies.di

import android.app.Application
import com.fakdl.moovies.di.auth.AuthComponent
import com.fakdl.moovies.di.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
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