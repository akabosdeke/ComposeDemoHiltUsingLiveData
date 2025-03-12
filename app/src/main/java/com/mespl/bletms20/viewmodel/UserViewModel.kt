package com.mespl.bletms20.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mespl.bletms20.BaseViewModel
import com.mespl.bletms20.data.model.LoginRequest
import com.mespl.bletms20.data.model.LoginResponse
import com.mespl.bletms20.data.model.User
import com.mespl.bletms20.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String> = _errorMessage

    fun fetchUsers() {
        _isLoading.value = true
        handleApiCall(
            apiCall = { userRepository.getUsers() },
            result = _users,
            onSuccess = { _isLoading.value = false },
            onError = { error ->
                _errorMessage.value = error.message ?: "Unknown Error"
                _isLoading.value = false
            }
        )
    }

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse
    fun userLogin(request: LoginRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = userRepository.userLogin(request)
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                } else {
                    _errorMessage.value = response.errorBody()?.string() ?: "Login failed"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}