package com.scotiabank.githubapp.repository

import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.dto.toRepo
import com.scotiabank.githubapp.dto.toUser
import com.scotiabank.githubapp.network.GithubApi
import com.scotiabank.githubapp.network.safeApiCall
import com.scotiabank.githubapp.network.Result
import retrofit2.HttpException
import retrofit2.Response


class UserRepository(private val api: GithubApi) {

    suspend fun getUser(userId: String): Result<User> {
        return safeApiCall {
            val response = api.getUser(userId)
            if (response.isSuccessful) {
                Response.success(response.body()!!.toUser())
            } else {
                throw HttpException(response)
            }
        }
    }

    suspend fun getUserRepos(userId: String): Result<List<Repo>> {
        return safeApiCall {
            val response = api.getUserRepos(userId)
            if (response.isSuccessful) {
                Response.success(response.body()!!.map { it.toRepo() })
            } else {
                throw HttpException(response)
            }
        }
    }
}