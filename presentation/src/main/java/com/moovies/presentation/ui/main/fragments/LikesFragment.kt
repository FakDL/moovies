package com.moovies.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.moovies.presentation.R
import com.moovies.presentation.viewmodels.main.LikesViewModel
import javax.inject.Inject

class LikesFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment() {

    private val viewModel: LikesViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        setupObservers()
        super.onViewCreated(view, savedInstanceState)
    }
}