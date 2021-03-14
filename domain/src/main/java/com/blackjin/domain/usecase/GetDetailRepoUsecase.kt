package com.blackjin.domain.usecase

import com.blackjin.domain.model.RepoDetail
import com.blackjin.domain.model.base.BaseResponse
import com.blackjin.domain.repository.RepoRepository
import com.blackjin.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetDetailRepoUsecase(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userName: String, repoName: String, callback: BaseResponse<RepoDetail>) {
        withContext(Dispatchers.Main) {
            try {
                callback.onLoading()

                withContext(Dispatchers.IO) {
                    val repoDeferred = async { repoRepository.getRepo(userName, repoName) }
                    val userDeferred = async { userRepository.getUser(userName) }

                    val repo = repoDeferred.await()
                    val user = userDeferred.await()

                    val repoDetail = RepoDetail(
                        title = repo.fullName,
                        repoName = repo.name,
                        ownerName = user.name,
                        ownerUrl = user.profileImgUrl,
                        followers = user.followers,
                        following = user.following,
                        description = repo.description,
                        language = repo.language,
                        updatedAt = repo.updatedAt,
                        stars = repo.stars
                    )

                    callback.onSuccess(repoDetail)
                }
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