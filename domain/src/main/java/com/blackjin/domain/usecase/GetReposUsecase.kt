package com.blackjin.domain.usecase

import com.blackjin.domain.model.Repos
import com.blackjin.domain.model.base.BaseResponse
import com.blackjin.domain.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReposUsecase(
    private val repoRepository: RepoRepository
) {

    suspend operator fun invoke(query: String, callback: BaseResponse<Repos>) {
        withContext(Dispatchers.Main) {
            try {
                callback.onLoading()

                val repos = repoRepository.searchRepos(query)
                callback.onSuccess(repos)
            } catch (e: Exception) {
                //TODO HttpException 어떻게 처리 할까?
                /*if (e is HttpException) {
                    callback.onFail(e.message())
                } else {
                    callback.onError(e)
                }*/
                callback.onError(e)
            }
            callback.onLoaded()
        }
    }
}