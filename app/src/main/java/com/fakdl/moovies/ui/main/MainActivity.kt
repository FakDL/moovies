package com.fakdl.moovies.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.fakdl.moovies.BaseApplication
import com.fakdl.moovies.R
import com.fakdl.moovies.fragments.main.MainNavHostFragment
import com.fakdl.moovies.ui.auth.AuthActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var navHost: NavHostFragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNavHost()
    }

    fun inject() {
        (application as BaseApplication).mainComponent()
            .inject(this)
    }

    fun navToAuth(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
        (application as BaseApplication).releaseMainComponent()
    }

    private fun createNavHost(){
         navHost = MainNavHostFragment.create(
            R.navigation.main_nav_graph
        )
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragments_container,
                navHost,
                getString(R.string.MainNavHost)
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }


}