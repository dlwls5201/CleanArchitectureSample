package com.example.toyproject.data.api

import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("search/repositories")
    suspend fun searchRepository(@Query("q") query: String): RepoSearchResponse

    @GET("repos/{owner}/{name}")
    suspend fun getRepository(
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String
    ): RepoModel
}