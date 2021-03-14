package com.example.toyproject.repository.fake

import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.Repo
import com.example.toyproject.data.model.RepoDetail
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*

class FakeRepoRepositoryImpl : RepoRepository {

    companion object {
        private const val DELAY_TIME = 5000L
    }

    override suspend fun searchRepositories(
        query: String, callback: BaseResponse<RepoSearchResponse>
    ) {
        withContext(Dispatchers.Main) {
            try {
                callback.onLoading()
                withContext(Dispatchers.IO) {
                    delay(DELAY_TIME)
                    val repo = RepoSearchResponse(
                        totalCount = 10,
                        items = listOf(
                            Repo(
                                name = "name",
                                fullName = "fullName",
                                owner = Repo.OwnerModel(
                                    login = "login",
                                    avatarUrl = ""
                                ),
                                description = null,
                                language = null,
                                updatedAt = Date(),
                                stars = 1000
                            )
                        )
                    )
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
                    delay(DELAY_TIME)
                    val repoDetail = RepoDetail(
                        title = "title",
                        repoName = "repoName",
                        ownerName = "ownerName",
                        ownerUrl = "",
                        followers = 1000,
                        following = 1000,
                        description = "description",
                        language = null,
                        updatedAt = Date(),
                        stars = 1000
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