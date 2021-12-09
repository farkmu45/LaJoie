package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tigro.lajoie.databinding.FragmentWallDetailBinding

class WallDetailFragment : Fragment() {

    private lateinit var binding: FragmentWallDetailBinding
    private val wallDetailViewModel: WallViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            viewModel = wallDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }


    }
}