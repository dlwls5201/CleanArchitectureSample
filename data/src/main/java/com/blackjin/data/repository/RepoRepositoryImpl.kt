package com.blackjin.data.repository

import com.blackjin.data.api.RepoApi
import com.blackjin.data.model.mapToDomain
import com.blackjin.domain.model.Repos
import com.blackjin.domain.repository.RepoRepository

class RepoRepositoryImpl(
    private val repoApi: RepoApi
) : RepoRepository {

    override suspend fun searchRepos(query: String): Repos {
        val response = repoApi.searchRepository(query)
        return response.mapToDomain()
    }

    override suspend fun getRepo(owner: String, repoName: String): Repos.Repo {
        val response = repoApi.getRepository(owner, repoName)
        return response.mapToDomain()
    }
}