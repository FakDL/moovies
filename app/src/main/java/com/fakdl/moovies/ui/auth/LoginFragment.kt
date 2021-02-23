package com.fakdl.moovies.ui.auth

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
import com.fakdl.moovies.R
import com.fakdl.moovies.di.auth.AuthScope
import com.fakdl.moovies.util.Constants.WRONG_ACC
import com.fakdl.moovies.util.Utils.md5
import com.fakdl.moovies.viewmodels.auth.LoginActionState
import com.fakdl.moovies.viewmodels.auth.LoginViewModel
import com.fakdl.moovies.viewmodels.auth.LoginViewState
import kotlinx.android.synthetic.main.fragment_login.*
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

    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
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
                    progressBar.visibility = ProgressBar.INVISIBLE
                }
                is LoginViewState.LoginFailed -> {
                    progressBar.visibility = ProgressBar.INVISIBLE
                    if (state.errorType == WRONG_ACC) {
                        Toast.makeText(activity, "Неверная почта или пароль", Toast.LENGTH_SHORT).show()
                    }
                }
                is LoginViewState.Loading -> {
                    progressBar.visibility = ProgressBar.VISIBLE
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
        btn_login.setOnClickListener{
            GlobalScope.launch {
                val users = viewModel.getAll()
                val email = input_email.text.toString()
                val hashPassword = md5(input_password.text.toString())
                viewModel.login(email, hashPassword)
            }
        }
        tv_no_acc.setOnClickListener {
            viewModel.goToRegistration()
        }
    }

}