package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tigro.lajoie.R
import com.tigro.lajoie.adapters.WallAdapter
import com.tigro.lajoie.databinding.FragmentWallBinding
import com.tigro.lajoie.screens.auth.AuthViewModel
import com.tigro.lajoie.utils.ApiStatus

class WallFragment : Fragment() {

    private lateinit var binding: FragmentWallBinding
    private val wallViewModel: WallViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (wallViewModel.wallData.value.isNullOrEmpty()) {
            wallViewModel.getData(authViewModel.token.value.toString())
        }

        binding = FragmentWallBinding.inflate(inflater)
        val wallAdapter = WallAdapter()
        binding.apply {
            viewModel = wallViewModel
            userViewModel = authViewModel
            lifecycleOwner = viewLifecycleOwner
            recycler.adapter = wallAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.status.observe(viewLifecycleOwner, {
            if (it.equals(ApiStatus.SUCCESS)) {
                binding.greet.text =
                    getString(R.string.wall_greeting, authViewModel.user.value!!.username)
            }
        })

        binding.apply {

            wallRefresh.setOnRefreshListener {
                wallViewModel.getData(authViewModel.token.value.toString())
            }

            addBtn.setOnClickListener {
                findNavController().navigate(R.id.action_wallFragment_to_newQuestionFragment)
            }

            historyBtn.setOnClickListener {
                findNavController().navigate(R.id.action_wallFragment_to_wallDraftFragment)
            }

            wallViewModel.status.observe(viewLifecycleOwner, {
                if (it.equals(ApiStatus.SUCCESS)) {
                    wallRefresh.isRefreshing = false
                    wallLoading.visibility = View.GONE
                } else if (it.equals(ApiStatus.FAILED)) {
                    wallRefresh.isRefreshing = false
                    wallLoading.visibility = View.GONE
                    Snackbar.make(
                        view,
                        getString(R.string.wall_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}