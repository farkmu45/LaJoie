package com.tigro.lajoie.screens.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.RegisterBody
import com.tigro.lajoie.utils.ApiStatus
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val fullname = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val username = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    private val _isValid = MutableLiveData(false)
    val isValid: LiveData<Boolean> = _isValid


    private val _isPasswordValid = MutableLiveData(false)
    val isPasswordValid: LiveData<Boolean> = _isPasswordValid

    private val _status = MutableLiveData(ApiStatus.INIT)
    val status: LiveData<ApiStatus> = _status


    fun register() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                LajoieApi.retrofitService.register(
                    RegisterBody(
                        email.value.toString(),
                        password.value.toString(),
                        fullname.value.toString(),
                        username.value.toString()
                    )
                )
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun checkData() {
        _status.value = ApiStatus.INIT
        _isValid.value = !(fullname.value!!.trim().isEmpty() || email.value!!.trim()
            .isEmpty() || password.value!!.trim().isEmpty() || username.value!!.trim()
            .isEmpty() || confirmPassword.value!!.trim()
            .isEmpty()) && (confirmPassword.value.equals(password.value))
    }


}