package com.moovies.presentation.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.moovies.domain.model.Film
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.R
import com.moovies.presentation.databinding.FragmentDetailsBinding
import com.moovies.presentation.databinding.FragmentFilmsBinding
import com.moovies.presentation.databinding.FragmentLikesBinding
import com.moovies.presentation.ui.main.MainActivity
import com.moovies.presentation.ui.main.recyclerview.FilmAdapter
import com.moovies.presentation.ui.main.recyclerview.loadstate.FilmLoadStateAdapter
import com.moovies.presentation.viewmodels.main.LikesViewModel
import com.moovies.presentation.viewmodels.main.ListFetchingViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LikesFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
) : Fragment() {

    private var _binding: FragmentLikesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FilmAdapter

    private val downloadImageHelper = DownloadImageHelper(requestManager)

    private val viewModel: LikesViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLikesBinding.inflate(inflater, container, false)

        return binding.root
    }

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Test", "films")
        setupObservers()
        loadList()
        setupAdapter()
    }

    @FlowPreview
    private fun loadList() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchData().collect {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        val clickLambda: (String) -> Unit = { id ->
            Log.d("Test", "id $id in clickLambda")
            val action = LikesFragmentDirections.actionLikesFragmentToDetailsFragment(id)
            (activity as MainActivity).navHost.navController.navigate(action)
        }
        adapter = FilmAdapter(clickLambda, downloadImageHelper)
        binding.rvLikes.adapter = adapter.withLoadStateFooter(
            footer = FilmLoadStateAdapter { adapter.retry() }
        )
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ListFetchingViewState.Success -> {
                    binding.pbLikes.isVisible = false
                }
                is ListFetchingViewState.Loading -> {
                    binding.pbLikes.isVisible = true
                }
                is ListFetchingViewState.FetchingFailed -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Test", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Test", "onDestroy")
    }
}