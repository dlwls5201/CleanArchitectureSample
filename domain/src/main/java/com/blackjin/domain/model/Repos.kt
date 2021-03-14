package com.blackjin.domain.model

import java.util.*

data class Repos(
    val totalCount: Int,
    val repos: List<Repo>
) {
    data class Repo(
        val name: String,
        val fullName: String,
        val owner: Owner,
        val description: String?,
        val language: String?,
        val updatedAt: Date,
        val stars: Int
    )

    data class Owner(
        val login: String,
        val avatarUrl: String
    )
}