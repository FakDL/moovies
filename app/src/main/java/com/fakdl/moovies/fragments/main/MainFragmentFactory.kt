package com.fakdl.moovies.fragments.main

import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.fakdl.moovies.di.main.MainScope
import com.fakdl.moovies.ui.main.FilmsFragment
import com.fakdl.moovies.ui.main.LikesFragment
import javax.inject.Inject

@MainScope
class MainFragmentFactory
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            FilmsFragment::class.java.name -> {
                FilmsFragment(sharedPreferences, viewModelFactory)
            }

            LikesFragment::class.java.name -> {
                LikesFragment(sharedPreferences, viewModelFactory)
            }

            else -> {
                FilmsFragment(sharedPreferences, viewModelFactory)
            }
        }
}
