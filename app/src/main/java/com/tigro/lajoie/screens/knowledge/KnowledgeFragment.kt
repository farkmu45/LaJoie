package com.tigro.lajoie.screens.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tigro.lajoie.adapters.KnowledgeAdapter
import com.tigro.lajoie.databinding.FragmentKnowledgeBinding
import com.tigro.lajoie.utils.ApiStatus

class KnowledgeFragment : Fragment() {
    private lateinit var binding: FragmentKnowledgeBinding
    private val knowledgeViewModel: KnowledgeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (knowledgeViewModel.knowledgeData.value.isNullOrEmpty()) {
            knowledgeViewModel.getData()
        }
        binding = FragmentKnowledgeBinding.inflate(inflater)

        binding.recycler.layoutManager = GridLayoutManager(context, 2)
        binding.recycler.adapter = KnowledgeAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = knowledgeViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}