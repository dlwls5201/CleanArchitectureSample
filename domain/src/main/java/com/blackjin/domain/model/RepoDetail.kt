package com.blackjin.domain.model

import java.util.*

data class RepoDetail(
    val title: String,
    val repoName: String,
    val ownerName: String,
    val ownerUrl: String,
    val followers: Int,
    val following: Int,
    val description: String?,
    val language: String?,
    val updatedAt: Date,
    val stars: Int
)