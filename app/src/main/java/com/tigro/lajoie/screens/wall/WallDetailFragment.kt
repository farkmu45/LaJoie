package com.tigro.lajoie.screens.wall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tigro.lajoie.R
import com.tigro.lajoie.adapters.CommentAdapter
import com.tigro.lajoie.databinding.FragmentWallDetailBinding
import com.tigro.lajoie.screens.auth.AuthViewModel
import com.tigro.lajoie.utils.ApiStatus

class WallDetailFragment : Fragment() {

    private lateinit var binding: FragmentWallDetailBinding
    private val wallDetailViewModel: WallViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val args: WallDetailFragmentArgs by navArgs()
    private var wallId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallDetailBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel = wallDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            wallIndex = args.knowledgeIndex
        }

        val index = args.knowledgeIndex
        wallId = wallDetailViewModel.wallData.value!![index].id

        wallDetailViewModel.getResponses(authViewModel.token.value!!, wallId)

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerComment.context,
            layoutManager.orientation
        )
        binding.recyclerComment.addItemDecoration(dividerItemDecoration)
        binding.recyclerComment.adapter = CommentAdapter()
        binding.recyclerComment.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog: AlertDialog =
            MaterialAlertDialogBuilder(context!!).setCancelable(false).setView(R.layout.progress)
                .create()

        wallDetailViewModel.commentStatus.observe(viewLifecycleOwner, {
            if (it.equals(ApiStatus.SUCCESS)) {
                dialog.dismiss()
                Snackbar.make(
                    view,
                    getString(R.string.comment_success),
                    Snackbar.LENGTH_SHORT
                ).show()
                wallDetailViewModel.getResponses(authViewModel.token.value!!, wallId)
                wallDetailViewModel.comment.value = ""
            } else if (it.equals(ApiStatus.FAILED)) {
                dialog.dismiss()
                Snackbar.make(
                    view,
                    getString(R.string.comment_failed),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            btnCommentSubmit.setOnClickListener {
                if (wallDetailViewModel.comment.value?.trim()?.isNotEmpty() == true) {
                    dialog.show()
                    wallDetailViewModel.addResponse(authViewModel.token.value!!, wallId)
                }
            }
        }
    }
}