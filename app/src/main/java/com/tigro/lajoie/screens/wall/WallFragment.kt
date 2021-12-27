package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.adapters.WallAdapter
import com.tigro.lajoie.databinding.FragmentWallBinding
import com.tigro.lajoie.screens.auth.AuthViewModel

class WallFragment : Fragment() {

    private lateinit var binding: FragmentWallBinding
    private val wallViewModel: WallViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        if (wallViewModel.wallData.value.isNullOrEmpty()) {
            wallViewModel.getData(authViewModel.token.value.toString())
        }
        binding = FragmentWallBinding.inflate(inflater)
        binding.viewModel = wallViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val wallAdapter = WallAdapter()

        binding.recycler.adapter = wallAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_wallFragment_to_newQuestionFragment)
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.history -> {
                    findNavController().navigate(R.id.action_wallFragment_to_wallDraftFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }
}