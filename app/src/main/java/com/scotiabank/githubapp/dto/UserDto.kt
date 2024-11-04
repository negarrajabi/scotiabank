package com.scotiabank.githubapp.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?
) : Serializable
