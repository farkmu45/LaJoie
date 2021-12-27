package com.tigro.lajoie.screens.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.models.User
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.LoginBody
import com.tigro.lajoie.utils.ApiStatus
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _user = MutableLiveData<User>()

    private val _token = MutableLiveData<String>()
    private val _status = MutableLiveData(ApiStatus.INIT)

    val status: LiveData<ApiStatus> = _status
    val token: LiveData<String> = _token
    val user: LiveData<User> = _user

    fun login() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val user =
                    LajoieApi.retrofitService.login(
                        LoginBody(
                            email.value.toString(), password.value.toString()
                        )
                    )
                _token.value = user.token
                _user.value = user
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("ERROR", e.message.toString())
                password.value = ""
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun setProfile(token: String) {
        viewModelScope.launch {
            try {
                val user =
                    LajoieApi.retrofitService.getUser(
                        "Basic $token"
                    )

                _user.value = user
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
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