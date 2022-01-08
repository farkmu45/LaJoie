package com.tigro.lajoie.screens.wall

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import com.tigro.lajoie.R
import com.tigro.lajoie.databinding.FragmentWallCreateBinding
import com.tigro.lajoie.screens.auth.AuthViewModel
import com.tigro.lajoie.utils.ApiStatus

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
            MaterialAlertDialogBuilder(context!!).setCancelable(false).setView(R.layout.progress)
                .create()

        wallViewModel.status.observe(viewLifecycleOwner, {
            when {
                it.equals(ApiStatus.SUCCESS) -> {
                    dialog.dismiss()
                    Toast.makeText(
                        context,
                        getString(R.string.wall_create_success),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_wallCreateFragment_to_wallFragment)
                }
                it.equals(ApiStatus.FAILED) -> {
                    dialog.dismiss()
                    Snackbar.make(
                        view,
                        getString(R.string.wall_create_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.btnSend.setOnClickListener {
            wallViewModel.sendQuestion(authViewModel.token.value.toString())
            dialog.show()
        }
        binding.toolbar.setupWithNavController(findNavController())
    }
}