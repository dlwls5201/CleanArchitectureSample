package com.example.toyproject.repository

import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetail
import com.example.toyproject.data.model.RepoSearchResponse

interface RepoRepository {

    suspend fun searchRepositories(query: String, callback: BaseResponse<RepoSearchResponse>)

    suspend fun getDetailRepository(
        user: String,
        repo: String,
        callback: BaseResponse<RepoDetail>
    )
}