package com.example.toyproject.ui.model

import android.content.Context
import android.text.TextUtils
import com.blackjin.data.model.Repo
import com.example.toyproject.R
import com.example.toyproject.utils.DateUtils

data class RepoItem(
    val title: String,
    val repoName: String,
    val owner: OwnerItem,
    val description: String?,
    val language: String?,
    val updatedAt: String,
    val stars: String
) {
    data class OwnerItem(
        val ownerName: String,
        val ownerUrl: String
    )
}

fun Repo.mapToPresentation(context: Context) = RepoItem(
    title = fullName,
    repoName = name,
    owner = RepoItem.OwnerItem(
        ownerName = owner.login,
        ownerUrl = owner.avatarUrl
    ),

    description = if (TextUtils.isEmpty(description))
        context.resources.getString(R.string.no_description_provided)
    else
        description,

    language = if (TextUtils.isEmpty(language))
        context.resources.getString(R.string.no_language_specified)
    else
        language,

    updatedAt = try {
        DateUtils.dateFormatToShow.format(updatedAt)
    } catch (e: IllegalArgumentException) {
        context.resources.getString(R.string.unknown)
    },

    stars = context.resources.getQuantityString(R.plurals.star, stars, stars)
)