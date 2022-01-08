package com.tigro.lajoie.screens.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.tigro.lajoie.R
import com.tigro.lajoie.adapters.KnowledgeCategoryAdapter
import com.tigro.lajoie.databinding.FragmentKnowledgeCategoryBinding
import com.tigro.lajoie.utils.ApiStatus

class KnowledgeCategoryFragment : Fragment() {

    private lateinit var binding: FragmentKnowledgeCategoryBinding
    private val knowledgeViewModel: KnowledgeViewModel by activityViewModels()
    private val args: KnowledgeCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKnowledgeCategoryBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = knowledgeViewModel
        }
        knowledgeViewModel.getDetails(args.knowledgeId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            toolbar.title = args.knowledgeName

            knowledgeCategoryRefresh.setOnRefreshListener {
                knowledgeViewModel.getDetails(args.knowledgeId)
            }

            knowledgeViewModel.status.observe(viewLifecycleOwner, {
                if (it.equals(ApiStatus.SUCCESS)) {
                    recycler.adapter =
                        KnowledgeCategoryAdapter(knowledgeViewModel.knowledgeDetailData.value!!)
                    knowledgeCategoryRefresh.isRefreshing = false
                    categoryLoading.visibility = View.GONE
                } else if (it.equals(ApiStatus.FAILED)) {
                    knowledgeCategoryRefresh.isRefreshing = false
                    categoryLoading.visibility = View.GONE
                    Snackbar.make(
                        view,
                        getString(R.string.knowledge_category_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}