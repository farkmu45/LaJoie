package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.tigro.lajoie.databinding.FragmentWallDetailBinding

class WallDetailFragment : Fragment() {

    private lateinit var binding: FragmentWallDetailBinding
    private val wallDetailViewModel: WallViewModel by activityViewModels()
    private val args: WallDetailFragmentArgs by navArgs()

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
            wallIndex = args.knowledgeIndex
        }

        Log.d("Test", wallDetailViewModel.wallData.value!![0].title.toString())


    }
}