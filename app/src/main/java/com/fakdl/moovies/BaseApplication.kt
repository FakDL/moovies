package com.fakdl.moovies

import android.app.Application
import com.fakdl.moovies.di.AppComponent
import com.fakdl.moovies.di.DaggerAppComponent
import com.fakdl.moovies.di.auth.AuthComponent
import com.fakdl.moovies.di.main.MainComponent

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


    fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
