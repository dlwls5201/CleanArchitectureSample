package com.blackjin.data.api

import com.blackjin.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/{name}")
    suspend fun getUser(@Path("name") userName: String): UserModel
}