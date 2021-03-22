package com.moovies.presentation.ui.main.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.moovies.presentation.R
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.ui.main.MainActivity
import com.moovies.presentation.ui.main.recyclerview.FilmAdapter
import com.moovies.presentation.ui.main.recyclerview.loadstate.FilmLoadStateAdapter
import com.moovies.presentation.viewmodels.main.ListFetchingViewState
import com.moovies.presentation.viewmodels.main.FilmsViewModel
import kotlinx.android.synthetic.main.fragment_films.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class FilmsFragment
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
): Fragment() {

    private lateinit var adapter: FilmAdapter

    private val viewModel: FilmsViewModel by viewModels {
        viewModelFactory
    }

    private val downloadImageHelper = DownloadImageHelper(requestManager)


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
        setListeners()
        setupAdapter()
        loadList()
        setupLayoutManager()
        setupObservers()
    }

    private fun loadList() {
        Log.d("Test", "Start")
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchData().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupLayoutManager() {
        rv_films.layoutManager =
            GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
    }

    private fun setListeners() {
               swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            loadList()
            swipe_refresh.isRefreshing = false
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
                is ListFetchingViewState.Success -> {
                }
                is ListFetchingViewState.Loading -> {
                }
                is ListFetchingViewState.FetchingFailed -> {
                    tv_films.text = "ERROR"
                }
            }
        })
    }

    private fun setupAdapter() {
        val clickLambda:(Long) -> Unit =  { id ->
            val action = FilmsFragmentDirections.actionFilmsFragmentToDetailsFragment(id)
            (activity as MainActivity).navHost.navController.
            navigate(action)
        }
        adapter = FilmAdapter(clickLambda, downloadImageHelper)
        rv_films.adapter = adapter.withLoadStateFooter(
            footer = FilmLoadStateAdapter{adapter.retry()}
        )
    }


}