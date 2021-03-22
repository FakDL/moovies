package com.moovies.presentation.factories.main

import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.moovies.data.di.main.MainScope
import com.moovies.presentation.ui.main.fragments.AccountFragment
import com.moovies.presentation.ui.main.fragments.DetailsFragment
import com.moovies.presentation.ui.main.fragments.FilmsFragment
import com.moovies.presentation.ui.main.fragments.LikesFragment
import javax.inject.Inject

@MainScope
class MainFragmentFactory
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            FilmsFragment::class.java.name -> {
                FilmsFragment(sharedPreferences, viewModelFactory, requestManager)
            }

            LikesFragment::class.java.name -> {
                LikesFragment( viewModelFactory)
            }

            DetailsFragment::class.java.name -> {
                DetailsFragment( viewModelFactory)
            }

            AccountFragment::class.java.name -> {
                AccountFragment(sharedPreferences, viewModelFactory)
            }

            else -> {
                FilmsFragment(sharedPreferences, viewModelFactory, requestManager)
            }
        }
}
