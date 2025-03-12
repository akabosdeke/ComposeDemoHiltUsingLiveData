package com.mespl.bletms20

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    open val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    open val errorMessage: LiveData<String> = _errorMessage

    protected fun <T> handleApiCall(
        apiCall: suspend () -> T,
        result: MutableLiveData<T>,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true) // Show Progress
                val response = apiCall()
                result.postValue(response)
                onSuccess?.invoke()
            } catch (e: Exception) {
                val errorMsg = e.localizedMessage ?: "Something went wrong"
                _errorMessage.postValue(errorMsg)
                onError?.invoke(e)
            } finally {
                _isLoading.postValue(false) // Hide Progress
            }
        }
    }
}