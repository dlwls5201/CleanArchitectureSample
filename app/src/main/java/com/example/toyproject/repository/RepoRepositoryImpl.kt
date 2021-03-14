package com.example.toyproject.repository

import com.example.toyproject.data.api.RepoApi
import com.example.toyproject.data.api.UserApi
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetail
import com.example.toyproject.data.model.RepoSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RepoRepositoryImpl(
    private val repoApi: RepoApi,
    private val userApi: UserApi
) : RepoRepository {

    override suspend fun searchRepositories(
        query: String,
        callback: BaseResponse<RepoSearchResponse>
    ) {
        withContext(Dispatchers.Main) {
            try {
                callback.onLoading()
                withContext(Dispatchers.IO) {
                    val repo = repoApi.searchRepository(query)
                    callback.onSuccess(repo)
                }
            } catch (e: Exception) {
                if (e is HttpException) {
                    callback.onFail(e.message())
                } else {
                    callback.onError(e)
                }
            }
            callback.onLoaded()
        }
    }

    override suspend fun getDetailRepository(
        user: String, repo: String, callback: BaseResponse<RepoDetail>
    ) {
        withContext(Dispatchers.Main) {
            try {
                callback.onLoading()
                withContext(Dispatchers.IO) {
                    val repoDeferred = async { repoApi.getRepository(user, repo) }
                    val userDeferred = async { userApi.getUser(user) }

                    val repoModel = repoDeferred.await()
                    val userModel = userDeferred.await()

                    val repoDetail = RepoDetail(
                        title = repoModel.fullName,
                        repoName = repoModel.name,
                        ownerName = userModel.name,
                        ownerUrl = userModel.profileImgUrl,
                        followers = userModel.followers,
                        following = userModel.following,
                        description = repoModel.description,
                        language = repoModel.language,
                        updatedAt = repoModel.updatedAt,
                        stars = repoModel.stars
                    )

                    callback.onSuccess(repoDetail)
                }
            } catch (e: Exception) {
                if (e is HttpException) {
                    callback.onFail(e.message())
                } else {
                    callback.onError(e)
                }
            }
            callback.onLoaded()
        }
    }
}