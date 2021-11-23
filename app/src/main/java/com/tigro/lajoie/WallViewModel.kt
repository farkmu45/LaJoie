package com.tigro.lajoie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.models.Wall
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.screens.auth.ApiStatus
import kotlinx.coroutines.launch

class WallViewModel() : ViewModel() {

    private val _wallData = MutableLiveData<List<Wall>>()
    val wallData: LiveData<List<Wall>> = _wallData

    private val _status = MutableLiveData(ApiStatus.LOADING)
    val status: LiveData<ApiStatus> = _status

    fun getData(token: String) {

        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _wallData.value = LajoieApi.retrofitService.getWalls("Basic $token")
                _status.value = ApiStatus.SUCCESS

            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }
}