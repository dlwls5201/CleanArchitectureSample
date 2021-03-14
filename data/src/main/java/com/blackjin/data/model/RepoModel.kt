package com.blackjin.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val ownerModel: OwnerModel,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("stargazers_count")
    val stars: Int
) {
    data class OwnerModel(
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    )
}
