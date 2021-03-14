package com.blackjin.domain.model

data class User(
    val name: String,
    val profileImgUrl: String,
    val followers: Int,
    val following: Int
)