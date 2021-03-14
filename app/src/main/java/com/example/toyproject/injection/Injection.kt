package com.example.toyproject.injection

import com.blackjin.data.api.ApiProvider
import com.blackjin.data.repository.RepoRepository
import com.blackjin.data.repository.RepoRepositoryImpl

object Injection {

    fun provideRepoRepository(): RepoRepository {
        return RepoRepositoryImpl(
            ApiProvider.provideRepoApi(),
            ApiProvider.provideUserApi()
        )
    }
}