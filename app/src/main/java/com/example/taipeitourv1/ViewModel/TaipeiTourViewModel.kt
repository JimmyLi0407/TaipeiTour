package com.example.taipeitourv1.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taipeitourv1.DataClass.TaipeiTourData
import com.example.taipeitourv1.Repository.TaipeiTourRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class TaipeiTourViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "TaipeiTourViewModel"
    private val taipeiTourRepository = TaipeiTourRepository()

    private val _taipeitourData = MutableLiveData<ArrayList<TaipeiTourData>>()
    val taipeitourData: LiveData<ArrayList<TaipeiTourData>> get() = _taipeitourData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError: LiveData<Boolean> get() = _loadingError

    fun fetchTaipeiTourData(language: String) {
        viewModelScope.launch {
            try {
                _loadingError.value = false
                _loading.value = true
                val taipeiTour = taipeiTourRepository.getTaipeiTourData(language)
                _taipeitourData.value = taipeiTour
                _loading.value = false
            } catch (e: Exception) {
                _loadingError.value = true
                Log.e(TAG, "Fetch Taipei Travel Data Error, $e")
            }
        }
    }
}