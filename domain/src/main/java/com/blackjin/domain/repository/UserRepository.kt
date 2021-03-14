package com.blackjin.domain.repository

import com.blackjin.domain.model.User

interface UserRepository {

    suspend fun getUser(userName: String): User
}