package com.tigro.lajoie.screens.submission

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tigro.lajoie.MainActivity
import com.tigro.lajoie.databinding.FragmentSubmissionBinding
import com.tigro.lajoie.screens.auth.AuthViewModel
import com.tigro.lajoie.utils.ApiStatus
import com.uploadcare.android.widget.controller.FileType
import com.uploadcare.android.widget.controller.UploadcareWidget
import com.uploadcare.android.widget.controller.UploadcareWidgetResult

class SubmissionFragment : Fragment() {

    private lateinit var binding: FragmentSubmissionBinding
    private val submissionViewModel: SubmissionViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubmissionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = submissionViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = authViewModel.token.value.toString()

        val dialog: AlertDialog =
            MaterialAlertDialogBuilder(context!!).setMessage("Submitting...").create()

        val confirmationDialog = MaterialAlertDialogBuilder(context!!)
            .setTitle("Confirmation")
            .setMessage("You can't use your account until your submission is reviewed, are you sure?")
            .setNegativeButton("No") { _, _ ->
            }
            .setPositiveButton("Yes") { _, _ ->
                submissionViewModel.sendSubmission(token)
                dialog.show()
            }.create()

        val successDialog =
            MaterialAlertDialogBuilder(context!!).setMessage("Your submission succesfully sent, you will be logged out once you click ok")
                .setPositiveButton("Yes") { _, _ ->
                    logout()
                }.create()

        binding.apply {
            toolbar.setupWithNavController(findNavController())
            btnFileUpload.setOnClickListener {
                pickImage()
            }

            btnSendSubmission.setOnClickListener {
                confirmationDialog.show()
            }
        }


        submissionViewModel.status.observe(viewLifecycleOwner, {
            when {
                it.equals(ApiStatus.FAILED) -> {
                    dialog.dismiss()
                    Toast.makeText(
                        context,
                        "There's an error while sending your submission",
                        Toast.LENGTH_LONG
                    ).show()
                }
                it.equals(ApiStatus.SUCCESS) -> {
                    dialog.dismiss()
                    successDialog.show()
                }
            }
        })

    }

    private fun logout() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putString("token", null)
            commit()
        }

        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun pickImage() {
        val fragment = this
        UploadcareWidget.getInstance()
            .selectFile(fragment)
            .fileType(FileType.image)
            .launch()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = UploadcareWidgetResult.fromIntent(data)
        result?.let {
            if (it.isSuccess()) {
                if (!submissionViewModel.cdn.value.isNullOrEmpty()) {
                    submissionViewModel.deleteImage(submissionViewModel.cdn.value!!)
                }
                submissionViewModel.setCdn(it.uploadcareFile?.uuid.toString())
            }
        }
    }

}