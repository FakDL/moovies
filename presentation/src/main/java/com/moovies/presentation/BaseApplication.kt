package com.moovies.presentation

import android.app.Application
import com.moovies.presentation.di.AppComponent
import com.moovies.presentation.di.DaggerAppComponent
import com.moovies.presentation.di.auth.AuthComponent
import com.moovies.presentation.di.main.MainComponent

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    private var mainComponent: MainComponent? = null
    private var authComponent: AuthComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    fun mainComponent(): MainComponent {
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent as MainComponent
    }

    fun releaseAuthComponent(){
        authComponent = null
    }

    fun authComponent(): AuthComponent {
        if(authComponent == null){
            authComponent = appComponent.authComponent().create()
        }
        return authComponent as AuthComponent
    }


    private fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
