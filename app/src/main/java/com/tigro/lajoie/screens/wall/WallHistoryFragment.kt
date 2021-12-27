package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tigro.lajoie.adapters.HistoryAdapter
import com.tigro.lajoie.databinding.FragmentWallHistoryBinding
import com.tigro.lajoie.screens.auth.AuthViewModel


class WallHistoryFragment : Fragment() {

    private lateinit var binding: FragmentWallHistoryBinding
    private val historyViewModel: WallViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallHistoryBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = historyViewModel
        }

        if (historyViewModel.historyData.value.isNullOrEmpty()) {
            historyViewModel.getHistory(authViewModel.token.value!!)
        }

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerHistory.context,
            layoutManager.orientation
        )
        binding.recyclerHistory.addItemDecoration(dividerItemDecoration)
        binding.recyclerHistory.adapter = HistoryAdapter()
        binding.recyclerHistory.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

    }
}