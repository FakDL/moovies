package com.moovies.presentation.ui.main.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moovies.presentation.R
import com.moovies.presentation.ui.main.MainActivity
import com.moovies.presentation.viewmodels.main.AccountViewModel
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountFragment
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel: AccountViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        lifecycleScope.launch {
        val user =  async { viewModel.getUserData()}.await()
        tv_email.text = tv_email.text.toString()+ " " + user.email
        tv_username.text = tv_username.text.toString() + " " +user.username
        }
    }

    private fun setupListeners() {
        btn_logout.setOnClickListener {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            (activity as MainActivity).navToAuth()
        }
    }

}