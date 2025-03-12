package com.mespl.bletms20.api

import android.database.Observable
import com.mespl.bletms20.data.model.LoginRequest
import com.mespl.bletms20.data.model.LoginResponse
import com.mespl.bletms20.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("LoginUser")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>
}
