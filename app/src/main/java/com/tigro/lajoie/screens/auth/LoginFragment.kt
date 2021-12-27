package com.tigro.lajoie.screens.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tigro.lajoie.R
import com.tigro.lajoie.databinding.FragmentLoginBinding
import com.tigro.lajoie.utils.ApiStatus

class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (checkLogin()) {
            authViewModel.setToken(getToken())
            authViewModel.setProfile(getToken())
            findNavController().navigate(R.id.action_loginFragment_to_wallFragment)
        }
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = authViewModel
        }

        authViewModel.status.observe(viewLifecycleOwner, {
            if (it.equals(ApiStatus.SUCCESS)) {
                if (authViewModel.user.value!!.status == "SUSPENDED") {
                    MaterialAlertDialogBuilder(context!!)
                        .setTitle("Login failed")
                        .setMessage("Your account is suspended until your document is validated")
                        .setPositiveButton("Ok") { _, _ ->
                        }.show()
                } else {
                    saveToken(authViewModel.token.value.toString())
                    findNavController().navigate(R.id.action_loginFragment_to_wallFragment)
                }

            }
        })


        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {
            authViewModel.login()
        }
    }

    private fun saveToken(token: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("token", token)
            apply()
        }
    }

    private fun getToken(): String {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", null)
        return token.toString()
    }

    private fun checkLogin(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        val token = sharedPref.getString("token", null)
        return token != null
    }
}