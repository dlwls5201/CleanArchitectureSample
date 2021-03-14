package com.example.toyproject.ui.model

import android.content.Context
import android.text.TextUtils
import com.blackjin.data.model.RepoDetail
import com.example.toyproject.R
import com.example.toyproject.utils.DateUtils

data class RepoDetailItem(
    val title: String,
    val repoName: String,
    val ownerName: String,
    val ownerUrl: String,
    val followers: String,
    val following: String,
    val description: String,
    val language: String,
    val updatedAt: String,
    val stars: String
)


fun RepoDetail.mapToPresentation(context: Context) = RepoDetailItem(
    title = title,
    repoName = repoName,
    ownerName = ownerName,
    ownerUrl = ownerUrl,
    followers = followers.let {
        if (it > 100) context.getString(R.string.max_follow_number) else it.toString()
    },
    following = following.let {
        if (it > 100) context.getString(R.string.max_follow_number) else it.toString()
    },
    description = if (TextUtils.isEmpty(description))
        context.resources.getString(R.string.no_description_provided)
    else
        description!!,
    language = if (TextUtils.isEmpty(language))
        context.resources.getString(R.string.no_language_specified)
    else
        language!!,
    updatedAt = try {
        DateUtils.dateFormatToShow.format(updatedAt)
    } catch (e: IllegalArgumentException) {
        context.resources.getString(R.string.unknown)
    },
    stars = context.resources.getQuantityString(R.plurals.star, stars, stars)
)