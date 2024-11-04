package com.scotiabank.githubapp.dto

import com.google.gson.annotations.SerializedName
import com.scotiabank.githubapp.domain.model.Repo
import java.io.Serializable

data class RepoDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("stargazers_count")
    val starCount: Int,
    @SerializedName("forks")
    val forks: Int,
) : Serializable


fun RepoDto.toRepo() = Repo(
    name,
    description,
    starCount,
    forks
)