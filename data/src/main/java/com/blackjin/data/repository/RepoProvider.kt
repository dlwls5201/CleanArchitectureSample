package com.blackjin.data.repository

import com.blackjin.data.api.ApiProvider
import com.blackjin.domain.repository.RepoRepository
import com.blackjin.domain.repository.UserRepository

object RepoProvider {

    fun provideRepoRepository(): RepoRepository =
        RepoRepositoryImpl(
            ApiProvider.provideRepoApi()
        )

    fun provideUserRepository(): UserRepository =
        UserRepositoryImpl(
            ApiProvider.provideUserApi()
        )
}