package com.moovies.presentation.ui.main.fragments

import android.content.SharedPreferences
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
import com.moovies.presentation.databinding.FragmentAccountBinding
import com.moovies.presentation.ui.main.MainActivity
import com.moovies.presentation.viewmodels.main.AccountViewModel
import com.moovies.presentation.viewmodels.main.AccountViewState
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountFragment
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val sharedPreferences: SharedPreferences
) : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        lifecycleScope.launch {
            viewModel.getUserData()
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is AccountViewState.Loading -> {
                    Log.d("Test", "Account Loading")
                    binding.clAcc.isVisible = false
                    binding.pbAcc.isVisible = true
                }
                is AccountViewState.Success -> {
                    Log.d("Test", "Account success")
                    binding.clAcc.isVisible = true
                    binding.pbAcc.isVisible = false
                    binding.tvEmail.text = tv_email.text.toString() + " " + state.user.email
                    binding.tvUsername.text = tv_username.text.toString() + " " + state.user.username
                }
            }
        })


    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            (activity as MainActivity).navToAuth()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
