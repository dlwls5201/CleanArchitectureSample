package com.blackjin.data.repository

import com.blackjin.data.api.UserApi
import com.blackjin.data.model.mapToDomain
import com.blackjin.domain.model.User
import com.blackjin.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getUser(userName: String): User {
        val response = userApi.getUser(userName)
        return response.mapToDomain()
    }
}