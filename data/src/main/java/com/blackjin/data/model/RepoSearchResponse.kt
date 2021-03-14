package com.blackjin.data.model

import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<Repo>
)