package com.scotiabank.githubapp.repository

import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.dto.toRepo
import com.scotiabank.githubapp.dto.toUser
import com.scotiabank.githubapp.network.GithubApi


class UserRepository(private val api: GithubApi) {

    suspend fun getUser(userId: String): User? =
        api.getUser(userId).body()?.toUser()


    suspend fun getUserRepos(userId: String): List<Repo> =
        api.getUserRepos(userId).body()?.map { it.toRepo() } ?: emptyList()

}