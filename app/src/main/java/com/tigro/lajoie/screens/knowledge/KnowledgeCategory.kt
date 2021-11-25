package com.tigro.lajoie.screens.knowledge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.tigro.lajoie.R
import com.tigro.lajoie.adapters.KnowledgeCategoryAdapter
import com.tigro.lajoie.databinding.FragmentKnowledgeCategoryBinding
import com.tigro.lajoie.screens.auth.ApiStatus

class KnowledgeCategory : Fragment() {

    private lateinit var binding: FragmentKnowledgeCategoryBinding
    private val knowledgeViewModel: KnowledgeViewModel by viewModels()
    private val args: KnowledgeCategoryArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKnowledgeCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setupWithNavController(findNavController())

        binding.toolbar.title = args.knowledgeName

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = knowledgeViewModel
        }

        knowledgeViewModel.getDetails(args.knowledgeId)

        knowledgeViewModel.status.observe(viewLifecycleOwner, {
            if (it.equals(ApiStatus.SUCCESS)) {
                binding.recycler.adapter =
                    KnowledgeCategoryAdapter(knowledgeViewModel.knowledgeDetailData.value!!)
            } else if(it.equals(ApiStatus.FAILED)) {
                Log.d("DATA", knowledgeViewModel.knowledgeDetailData.value!![1].name)
            }
        })
    }
}