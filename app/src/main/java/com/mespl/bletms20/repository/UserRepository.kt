package com.mespl.bletms20.repository

import com.mespl.bletms20.api.ApiService
import com.mespl.bletms20.data.model.LoginRequest
import com.mespl.bletms20.data.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUsers() = apiService.getUsers()

    suspend fun userLogin(request: LoginRequest): Response<LoginResponse> {
        return apiService.userLogin(request)
    }
}