package com.moovies.presentation.ui.main.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.moovies.presentation.R
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.databinding.FragmentFilmsBinding
import com.moovies.presentation.ui.main.MainActivity
import com.moovies.presentation.ui.main.recyclerview.FilmAdapter
import com.moovies.presentation.ui.main.recyclerview.loadstate.FilmLoadStateAdapter
import com.moovies.presentation.viewmodels.main.ListFetchingViewState
import com.moovies.presentation.viewmodels.main.FilmsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class FilmsFragment
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
) : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)

        return binding.root
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setupAdapter()
        loadList()
        setupObservers()
    }

    @FlowPreview
    private fun loadList() {
        Log.d("Test", "Start")
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchData().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    @FlowPreview
    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            loadList()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.films_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (query != null) {
                        val list = viewModel.findFilms(query)
                        adapter.submitData(PagingData.from(list))
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }
    //endregion

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ListFetchingViewState.Success -> {
                    binding.pbFilms.isVisible = false
                }
                is ListFetchingViewState.Loading -> {
                    binding.pbFilms.isVisible = true
                }
                is ListFetchingViewState.FetchingFailed -> {
                }
            }
        })
    }

    private fun setupAdapter() {
        val clickLambda: (String) -> Unit = { id ->
            Log.d("Test", "in fragm $id")
            val action = FilmsFragmentDirections.actionFilmsFragmentToDetailsFragment(id)
            (activity as MainActivity).navHost.navController.navigate(action)
        }
        adapter = FilmAdapter(clickLambda, downloadImageHelper)
        binding.rvFilms.adapter = adapter.withLoadStateFooter(
            footer = FilmLoadStateAdapter { adapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
