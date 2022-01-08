package com.tigro.lajoie.screens.wall

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.models.Comment
import com.tigro.lajoie.models.History
import com.tigro.lajoie.models.Wall
import com.tigro.lajoie.network.CommentBody
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.network.WallBody
import com.tigro.lajoie.utils.ApiStatus
import kotlinx.coroutines.launch

class WallViewModel() : ViewModel() {
    private val _wallData = MutableLiveData<List<Wall>>()
    val wallData: LiveData<List<Wall>> = _wallData

    private val _historyData = MutableLiveData<List<History>>()
    val historyData: LiveData<List<History>> = _historyData

    private val _commentData = MutableLiveData<List<Comment>>()
    val commentData: LiveData<List<Comment>> = _commentData

    private val _status = MutableLiveData(ApiStatus.INIT)
    val status: LiveData<ApiStatus> = _status

    private val _commentStatus = MutableLiveData(ApiStatus.INIT)
    val commentStatus: LiveData<ApiStatus> = _commentStatus

    val title = MutableLiveData("")
    val detail = MutableLiveData("")
    val comment = MutableLiveData("")

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

    fun sendQuestion(token: String) {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                LajoieApi.retrofitService.sendQuestion(
                    "Basic $token",
                    WallBody(title.value.toString(), detail.value.toString())
                )
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun getHistory(token: String) {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _historyData.value = LajoieApi.retrofitService.getHistory(
                    "Basic $token"
                )
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun getResponses(token: String, id: Int) {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _commentData.value = LajoieApi.retrofitService.getResponses(
                    "Basic $token", id
                )
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun addResponse(token: String, id: Int) {
        viewModelScope.launch {
            try {
                _commentStatus.value = ApiStatus.LOADING
                LajoieApi.retrofitService.addResponse(
                    CommentBody(comment.value!!), "Basic $token", id
                )
                _commentStatus.value = ApiStatus.SUCCESS
                _commentStatus.value = ApiStatus.INIT
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _commentStatus.value = ApiStatus.FAILED
            }
        }
    }
}