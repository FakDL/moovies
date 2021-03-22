package com.moovies.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
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

    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNavHost()
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
        navHost = MainNavHostFragment.create(
            R.navigation.main_nav_graph
        )
//        val navGraphIds = listOf(R.navigation.main_nav_graph)
//        val controller = btm_nav_bar.setupWithNavController(
//            navGraphIds = navGraphIds,
//            fragmentManager = supportFragmentManager,
//            containerId = R.id.main_fragments_container,
//            intent = intent
//        )
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragments_container,
                navHost,
                getString(R.string.MainNavHost)
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
//        controller.observe(this, { navController ->
//            setupActionBarWithNavController(navController)
//        })
//        currentNavController = controller
    }


}