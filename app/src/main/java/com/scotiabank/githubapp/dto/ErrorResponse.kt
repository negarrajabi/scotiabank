package com.scotiabank.githubapp.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("documentation_url")
    val documentation_url: String,
    @SerializedName("status")
    val status: String
) : Serializable