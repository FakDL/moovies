package com.moovies.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.moovies.presentation.R
import com.moovies.presentation.viewmodels.main.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private val filmId = args.filmId

    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObservers()
        viewModel.setFilmDetails(filmId)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer{
            tv_mock.text = it.toString()
        })
    }
}