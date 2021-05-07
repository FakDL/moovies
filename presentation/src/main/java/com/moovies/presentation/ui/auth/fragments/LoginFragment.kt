package com.moovies.presentation.ui.auth.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.moovies.presentation.R
import com.moovies.presentation.di.auth.AuthScope
import com.moovies.data.util.Constants.WRONG_ACC
import com.moovies.data.util.Utils.md5
import com.moovies.presentation.databinding.FragmentLoginBinding
import com.moovies.presentation.ui.auth.AuthActivity
import com.moovies.presentation.viewmodels.auth.LoginActionState
import com.moovies.presentation.viewmodels.auth.LoginViewModel
import com.moovies.presentation.viewmodels.auth.LoginViewState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AuthScope
class LoginFragment
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListeners()
        setupObservers()

        if(sharedPreferences.getBoolean("isLoggedIn", false)) {
            (activity as AuthActivity).navToMain()
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoginViewState.Success -> {
                    binding.progressBar.visibility = ProgressBar.INVISIBLE
                    binding.btnLogin.isClickable = true
                }
                is LoginViewState.LoginFailed -> {
                    binding.progressBar.visibility = ProgressBar.INVISIBLE
                    if (state.errorType == WRONG_ACC) {
                        Toast.makeText(activity, "Неверная почта или пароль", Toast.LENGTH_SHORT).show()
                    }
                    binding.btnLogin.isClickable = true
                }
                is LoginViewState.Loading -> {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                    binding.btnLogin.isClickable = false
                }
            }
        })
        viewModel.actionState.observe(viewLifecycleOwner, { state ->
            when(state) {
                is LoginActionState.NeedsRegistration -> {
                    (activity as AuthActivity).navHost.
                    navController.navigate(R.id.action_loginFragment_to_registrationFragment)
                }
                is LoginActionState.NeedsLogin -> {
                    (activity as AuthActivity).navToMain()
                }
            }
        })
    }

    fun setClickListeners() {
        binding.btnLogin.setOnClickListener{
            GlobalScope.launch {
                val email = binding.inputEmail.text.toString()
                val hashPassword = md5(binding.inputPassword.text.toString())
                viewModel.login(email, hashPassword)
            }
        }
        binding.tvNoAcc.setOnClickListener {
            viewModel.goToRegistration()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}