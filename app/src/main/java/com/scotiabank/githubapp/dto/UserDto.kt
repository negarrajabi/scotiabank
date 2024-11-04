package com.scotiabank.githubapp.dto

import com.google.gson.annotations.SerializedName
import com.scotiabank.githubapp.domain.model.User
import java.io.Serializable

data class UserDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?
) : Serializable


fun UserDto.toUser() = User(
    name,
    avatarUrl
)
