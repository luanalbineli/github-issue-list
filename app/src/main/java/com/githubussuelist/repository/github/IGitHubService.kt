package com.githubussuelist.repository.github

import com.githubussuelist.model.response.RepositoryIssueResponseModel
import com.githubussuelist.model.response.RepositoryResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitHubService {
    @GET("repos/{repositoryOrgName}/{repositoryName}")
    fun getRepositoryByName(@Path("repositoryOrgName") repositoryOrgName: String, @Path("repositoryName") repositoryName: String): Call<RepositoryResponseModel>

    @GET("repos/{repositoryOrgName}/{repositoryName}/issues")
    fun getRepositoryIssuesByRepositoryName(
        @Path("repositoryOrgName") repositoryOrgName: String, @Path(
            "repositoryName"
        ) repositoryName: String, @Query("page") page: Int
    ): Call<List<RepositoryIssueResponseModel>>
}