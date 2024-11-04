package com.scotiabank.githubapp.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repo(
    val name: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
)