package com.tigro.lajoie.screens.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.LoginBody
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, SUCCESS, FAILED, INIT }

class AuthViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _token = MutableLiveData<String>()
    private val _status = MutableLiveData(ApiStatus.INIT)

    val status: LiveData<ApiStatus> = _status
    val token: LiveData<String> = _token

    fun login() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val authToken =
                    LajoieApi.retrofitService.login(
                        LoginBody(
                            email.value.toString(), password.value.toString()
                        )
                    )
                _token.value = authToken.token
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
                password.value = ""
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun clearError() {
        _status.value = ApiStatus.INIT
    }

    fun setToken(token: String) {
        _token.value = token
    }
}