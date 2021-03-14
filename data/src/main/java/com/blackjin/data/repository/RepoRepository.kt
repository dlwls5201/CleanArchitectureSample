package com.blackjin.data.repository

import com.blackjin.data.base.BaseResponse
import com.blackjin.data.model.RepoDetail
import com.blackjin.data.model.RepoSearchResponse

interface RepoRepository {

    suspend fun searchRepositories(query: String, callback: BaseResponse<RepoSearchResponse>)

    suspend fun getDetailRepository(
        user: String,
        repo: String,
        callback: BaseResponse<RepoDetail>
    )
}