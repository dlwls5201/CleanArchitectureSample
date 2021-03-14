package com.blackjin.data.model

import com.blackjin.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("avatar_url")
    val profileImgUrl: String
)

fun UserModel.mapToDomain() = User(
    name = name,
    profileImgUrl = profileImgUrl,
    followers = followers,
    following = following
)