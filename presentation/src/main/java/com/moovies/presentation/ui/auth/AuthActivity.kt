package com.moovies.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.moovies.presentation.BaseApplication
import com.moovies.presentation.R
import com.moovies.presentation.factories.auth.AuthNavHostFragment
import com.moovies.presentation.ui.main.MainActivity
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    lateinit var navHost: AuthNavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        createNavHost()

    }

    fun inject() {
        (application as BaseApplication).authComponent()
            .inject(this)
    }

    private fun createNavHost(){
        navHost = AuthNavHostFragment.create(
            R.navigation.auth_nav_graph
        )

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.auth_fragments_container,
                navHost,
                getString(R.string.AuthNavHost)
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }
    fun navToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        (application as BaseApplication).releaseAuthComponent()
    }
}