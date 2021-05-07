package com.moovies.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.moovies.presentation.BaseApplication
import com.moovies.presentation.R
import com.moovies.presentation.factories.main.MainNavHostFragment
import com.moovies.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var navHost: NavHostFragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Test", savedInstanceState.toString())
        if(savedInstanceState == null){
            createNavHost()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        createNavHost()
        Log.d("Test", "re$savedInstanceState")
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
    }

    private fun setupNavigation() {
        btm_nav_bar.setupWithNavController(navHost.navController)
        btm_nav_bar.setOnNavigationItemReselectedListener {}
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
        Log.d("Test", "creating NAVHOST")
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

    override fun onSupportNavigateUp(): Boolean = navHost.navController.navigateUp()

}