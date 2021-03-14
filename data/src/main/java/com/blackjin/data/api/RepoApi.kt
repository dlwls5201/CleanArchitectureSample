package com.blackjin.data.api

import com.blackjin.data.model.RepoModel
import com.blackjin.data.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("search/repositories")
    suspend fun searchRepository(@Query("q") query: String): RepoResponse

    @GET("repos/{owner}/{name}")
    suspend fun getRepository(
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String
    ): RepoModel
}