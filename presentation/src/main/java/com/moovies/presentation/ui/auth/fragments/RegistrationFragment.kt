package com.moovies.presentation.ui.auth.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.moovies.presentation.R
import com.moovies.presentation.di.auth.AuthScope
import com.moovies.data.util.Constants.EMPTY_FIELDS
import com.moovies.data.util.Constants.WRONG_EMAIL
import com.moovies.data.util.Utils.md5
import com.moovies.presentation.ui.auth.AuthActivity
import com.moovies.presentation.viewmodels.auth.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.input_email
import kotlinx.android.synthetic.main.fragment_registration.input_password
import kotlinx.android.synthetic.main.fragment_registration.progressBar
import javax.inject.Inject

@AuthScope
class RegistrationFragment
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    val viewModel: RegistrationViewModel by viewModels {
        viewModelFactory
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setClickListeners()
        setupObservers()
        if(sharedPreferences.getBoolean("isLoggedIn", false)) {
            (activity as AuthActivity).navToMain()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    private fun setupObservers() {
        viewModel.actionState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is RegistrationActionState.NeedsLogin -> {
                    (activity as AuthActivity).navToMain()
                }
            }
        })
        viewModel.viewState.observe(viewLifecycleOwner,  { state ->
            when (state) {
                is RegistrationViewState.Success -> {
                    progressBar.visibility = ProgressBar.INVISIBLE
                }
                is RegistrationViewState.RegistrationFailed -> {
                    progressBar.visibility = ProgressBar.INVISIBLE
                    when (state.errorType) {
                        EMPTY_FIELDS -> {
                            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
                        }
                        WRONG_EMAIL -> {
                            Toast.makeText(activity, "Неверная почта", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(activity, "Разные пароли", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is RegistrationViewState.Loading -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
            }
        })
    }

    private fun setClickListeners() {
        btn_register.setOnClickListener{
            val email = input_email.text.toString()
            val username = input_username.text.toString()
            val hashPassword = md5(input_password.text.toString())
            val confirmHashPassword = md5(input_password_confirm.text.toString())
            viewModel.register(email, username,  hashPassword, confirmHashPassword)

        }
    }
}