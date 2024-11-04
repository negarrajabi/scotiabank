package com.scotiabank.githubapp.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val name: String?,
    val avatarUrl: String?
)