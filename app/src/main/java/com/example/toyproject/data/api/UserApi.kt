package com.example.toyproject.data.api

import com.example.toyproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{name}")
    suspend fun getUser(@Path("name") userName: String): User
}