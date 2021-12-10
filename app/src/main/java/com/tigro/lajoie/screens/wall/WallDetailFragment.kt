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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tigro.lajoie.adapters.CommentAdapter
import com.tigro.lajoie.databinding.FragmentWallDetailBinding
import com.tigro.lajoie.screens.auth.AuthViewModel
import com.tigro.lajoie.utils.ApiStatus

class WallDetailFragment : Fragment() {

    private lateinit var binding: FragmentWallDetailBinding
    private val wallDetailViewModel: WallViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
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

        wallDetailViewModel.status.observe(viewLifecycleOwner, {
            if (it.equals(ApiStatus.SUCCESS)) {
                val layoutManager = LinearLayoutManager(context)
                val dividerItemDecoration = DividerItemDecoration(
                    binding.recyclerComment.context,
                    layoutManager.orientation
                )
                binding.recyclerComment.addItemDecoration(dividerItemDecoration)
                binding.recyclerComment.adapter =
                    CommentAdapter(wallDetailViewModel.commentData.value!!)
                binding.recyclerComment.layoutManager = layoutManager
            }
        })

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            viewModel = wallDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            wallIndex = args.knowledgeIndex
        }

        val index = args.knowledgeIndex
        val wallId = wallDetailViewModel.wallData.value!![index].id

        Log.d("WallID", wallId.toString())

        wallDetailViewModel.getResponses(authViewModel.token.value!!, wallId)
    }
}