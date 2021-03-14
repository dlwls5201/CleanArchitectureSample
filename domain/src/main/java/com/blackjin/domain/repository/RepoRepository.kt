package com.blackjin.domain.repository

import com.blackjin.domain.model.Repos


interface RepoRepository {

    suspend fun searchRepos(query: String): Repos

    suspend fun getRepo(owner: String, repoName: String): Repos.Repo
}