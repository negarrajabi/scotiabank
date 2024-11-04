package com.scotiabank.githubapp.network

import com.scotiabank.githubapp.dto.RepoDto
import com.scotiabank.githubapp.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<UserDto>

    @GET("users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): Response<List<RepoDto>>
}