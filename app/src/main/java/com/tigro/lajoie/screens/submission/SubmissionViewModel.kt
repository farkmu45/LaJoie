package com.tigro.lajoie.screens.submission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.SubmissionBody
import com.tigro.lajoie.utils.ApiStatus
import kotlinx.coroutines.launch

class SubmissionViewModel : ViewModel() {
    private val _cdn = MutableLiveData("")
    val cdn: LiveData<String> = _cdn
    val experience = MutableLiveData("")


    private val _status = MutableLiveData(ApiStatus.INIT)
    val status: LiveData<ApiStatus> = _status

    fun setCdn(url: String) {
        _cdn.value = url
    }

    fun deleteImage(uuid: String) {
        try {
            val imageUrl = "https://api.uploadcare.com/files/${uuid}/storage/"
            viewModelScope.launch {
                LajoieApi.retrofitService.deleteImage(imageUrl)
            }
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        }
    }

    fun sendSubmission(token: String) {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                LajoieApi.retrofitService.sendSubmission(
                    SubmissionBody(experience.value!!, _cdn.value!!), "Basic $token"
                )
                _status.value = ApiStatus.SUCCESS
                _status.value = ApiStatus.INIT
            } catch (e: Exception) {
                Log.d("SubmissionError", e.message.toString())
            }
        }

    }
}