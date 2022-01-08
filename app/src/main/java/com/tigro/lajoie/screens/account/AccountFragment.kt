package com.tigro.lajoie.screens.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tigro.lajoie.MainActivity
import com.tigro.lajoie.adapters.AccountAdapter
import com.tigro.lajoie.databinding.FragmentAccountBinding
import com.tigro.lajoie.models.expertScreenData
import com.tigro.lajoie.models.regularScreenData
import com.tigro.lajoie.screens.auth.AuthViewModel

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = authViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val screenData =
            if (authViewModel.user.value!!.type == 1) regularScreenData else expertScreenData
        binding.apply {
            recyclerAccount.adapter = AccountAdapter(screenData) {
                MaterialAlertDialogBuilder(context!!)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton("No") { _, _ ->
                    }
                    .setPositiveButton("Yes") { _, _ ->
                        logout()
                    }.show()
            }
        }
    }

    private fun logout() {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putString("token", null)
            commit()
        }

        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}