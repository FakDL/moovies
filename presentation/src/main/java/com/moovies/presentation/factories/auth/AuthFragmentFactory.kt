package com.moovies.presentation.factories.auth

import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.moovies.presentation.di.auth.AuthScope
import com.moovies.presentation.ui.auth.fragments.LoginFragment
import com.moovies.presentation.ui.auth.fragments.RegistrationFragment
import javax.inject.Inject

@AuthScope
class AuthFragmentFactory
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            LoginFragment::class.java.name -> {
                LoginFragment(sharedPreferences, viewModelFactory)
            }

            RegistrationFragment::class.java.name -> {
                RegistrationFragment(sharedPreferences, viewModelFactory)
            }

            else -> {
                LoginFragment(sharedPreferences, viewModelFactory)
            }
        }


}