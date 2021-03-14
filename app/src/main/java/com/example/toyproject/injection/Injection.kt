package com.example.toyproject.injection

import com.blackjin.data.repository.RepoProvider
import com.blackjin.domain.usecase.GetDetailRepoUsecase
import com.blackjin.domain.usecase.GetReposUsecase

object Injection {

    fun provideGetReposUsecase() = GetReposUsecase(
        RepoProvider.provideRepoRepository()
    )

    fun provideGetDetailRepoUsecase() = GetDetailRepoUsecase(
        RepoProvider.provideRepoRepository(),
        RepoProvider.provideUserRepository()
    )
}