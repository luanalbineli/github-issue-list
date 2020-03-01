package com.githubussuelist.repository.github

import com.githubussuelist.model.response.RepositoryIssueResponseModel
import com.githubussuelist.model.response.RepositoryResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitHubService {
    @GET("repos/{repositoryName}")
    fun getRepositoryByName(@Path("repositoryName") repositoryName: String) : Call<RepositoryResponseModel>

    @GET("repos/{repositoryName}/issues")
    fun getRepositoryIssuesByRepositoryName(@Path("repositoryName") stateId: String, @Query("page") page: Int) : Call<List<RepositoryIssueResponseModel>>
}