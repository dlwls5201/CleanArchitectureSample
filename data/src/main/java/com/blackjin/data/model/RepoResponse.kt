package com.blackjin.data.model

import com.blackjin.domain.model.Repos
import com.google.gson.annotations.SerializedName

data class RepoResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<RepoModel>
)

fun RepoResponse.mapToDomain() = Repos(
    totalCount = totalCount,
    repos = items.map { it.mapToDomain() })

fun RepoModel.mapToDomain() = Repos.Repo(
    name = name,
    fullName = fullName,
    owner = Repos.Owner(
        login = ownerModel.login,
        avatarUrl = ownerModel.avatarUrl
    ),
    description = description,
    language = language,
    updatedAt = updatedAt,
    stars = stars
)

