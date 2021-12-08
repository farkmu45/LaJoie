package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tigro.lajoie.R
import com.tigro.lajoie.databinding.FragmentWallCreateBinding
import com.tigro.lajoie.screens.auth.ApiStatus
import com.tigro.lajoie.screens.auth.AuthViewModel

class WallCreateFragment : Fragment() {
    private lateinit var binding: FragmentWallCreateBinding
    private val wallViewModel: WallViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = wallViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        val dialog: AlertDialog =
            MaterialAlertDialogBuilder(context!!).setMessage("Submitting...").create()

        wallViewModel.status.observe(viewLifecycleOwner, {
            when {
                it.equals(ApiStatus.SUCCESS) -> {
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_wallCreateFragment_to_wallFragment)
                    Toast.makeText(context, "Question sent", Toast.LENGTH_LONG).show()
                }
                it.equals(ApiStatus.FAILED) -> {
                    dialog.dismiss()
                    Toast.makeText(
                        context,
                        "There's a problem submitting your question",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        binding.btnSend.setOnClickListener {
            Log.d("Token", authViewModel.token.toString())
            wallViewModel.sendQuestion(authViewModel.token.value.toString())
            dialog.show()
        }
        binding.toolbar.setupWithNavController(findNavController())
    }
}