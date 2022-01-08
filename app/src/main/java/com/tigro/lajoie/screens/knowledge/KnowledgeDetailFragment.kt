package com.tigro.lajoie.screens.knowledge

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.tigro.lajoie.databinding.FragmentKnowledgeDetailBinding

class KnowledgeDetailFragment : Fragment() {

    private lateinit var binding: FragmentKnowledgeDetailBinding
    private val args: KnowledgeDetailFragmentArgs by navArgs()
    private val knowledgeViewModel: KnowledgeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKnowledgeDetailBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel = knowledgeViewModel
            lifecycleOwner = viewLifecycleOwner
            knowledgeIndex = args.knowledgeIndex
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())
        binding.knowledgeDetail.movementMethod = LinkMovementMethod.getInstance()
    }

}