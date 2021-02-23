package com.fakdl.moovies.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.fakdl.moovies.R
import com.fakdl.moovies.ui.main.recyclerview.FilmAdapter
import com.fakdl.moovies.viewmodels.main.FetchingViewState
import com.fakdl.moovies.viewmodels.main.FilmsViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import javax.inject.Inject


class FilmsFragment
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment() {

    private val viewModel: FilmsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setupObservers()
        viewModel.fetchData()
    }

    private fun setOnClickListeners() {
        btn_to_likes.setOnClickListener{
            (activity as MainActivity).navHost.navController.navigate(R.id.action_filmsFragment_to_likesFragment)
        }
    }

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.films_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.action_log_out -> {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            (activity as MainActivity).navToAuth()
            true
        }

        else -> false
    }
    //endregion

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner,{state ->
            when(state) {
                is FetchingViewState.Success -> {
                    rv_films.adapter = FilmAdapter(state.films){ id ->

                    }
                }
                is FetchingViewState.FetchingFailed -> {
                    tv_films.text = "ERROR"
                }
            }
        })
    }

}