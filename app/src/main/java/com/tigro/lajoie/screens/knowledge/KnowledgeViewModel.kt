package com.tigro.lajoie.screens.knowledge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigro.lajoie.models.Knowledge
import com.tigro.lajoie.network.LajoieApi
import com.tigro.lajoie.screens.auth.ApiStatus
import kotlinx.coroutines.launch

class KnowledgeViewModel() : ViewModel() {

    private val _knowledgeData = MutableLiveData<List<Knowledge>>()
    val knowledgeData: LiveData<List<Knowledge>> = _knowledgeData

    private val _status = MutableLiveData(ApiStatus.LOADING)
    val status: LiveData<ApiStatus> = _status

    fun getData() {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                _knowledgeData.value = LajoieApi.retrofitService.getKnowledges()
                _status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                _status.value = ApiStatus.FAILED
            }
        }
    }
}