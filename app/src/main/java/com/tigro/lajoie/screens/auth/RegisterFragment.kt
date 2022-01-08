package com.tigro.lajoie.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.tigro.lajoie.R
import com.tigro.lajoie.databinding.FragmentRegisterBinding
import com.tigro.lajoie.utils.ApiStatus


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingDialog: AlertDialog =
            MaterialAlertDialogBuilder(context!!).setCancelable(false).setView(R.layout.progress)
                .create()

        binding.toolbar.setupWithNavController(findNavController())


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = registerViewModel
        }

        registerViewModel.apply {
            confirmPassword.observe(viewLifecycleOwner, {
                registerViewModel.checkData()
                if (!it.equals(registerViewModel.password.value)) {
                    binding.confirmPassword.error = "Password confirmation doesn't match"
                    binding.confirmPassword.isErrorEnabled = true
                } else {
                    binding.confirmPassword.error = null
                    binding.confirmPassword.isErrorEnabled = false
                }
            })

            status.observe(viewLifecycleOwner, {
                if (it.equals(ApiStatus.SUCCESS)) {
                    loadingDialog.dismiss()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else if (it.equals(ApiStatus.FAILED)) {
                    loadingDialog.dismiss()
                }
            })
        }
        binding.apply {
            fullname.editText?.let { checkOnType(it, fullname) }
            username.editText?.let { checkOnType(it, username) }
            password.editText?.let { checkOnType(it, password) }
            email.editText?.let { checkOnType(it, email) }
        }

        binding.registerBtn.setOnClickListener {
            loadingDialog.show()
            registerViewModel.register()
        }
    }


    private fun checkOnType(
        editText: EditText,
        textInput: TextInputLayout,
        errorText: String = "This data cannot be empty"
    ) {
        editText.doAfterTextChanged {
            registerViewModel.checkData()
            if (it!!.trim().isEmpty()) {
                textInput.error = errorText
                textInput.isErrorEnabled = true
            } else {
                textInput.error = null
                textInput.isErrorEnabled = false
            }
        }
    }
}