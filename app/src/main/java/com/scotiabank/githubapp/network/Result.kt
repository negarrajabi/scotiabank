package com.scotiabank.githubapp.network

import com.google.gson.Gson
import com.scotiabank.githubapp.dto.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String, val cause: Exception? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object NotLoaded : Result<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Response body is null")
                }
            } else {
                // Handle error body and parse status and message
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                val errorMessage =
                    "Status: ${errorResponse.status}, Message: ${errorResponse.message}"
                Result.Error(errorMessage)
            }
        } catch (e: IOException) {
            Result.Error("Network error. Please check your connection.", e)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string() ?: "HTTP error: ${e.message()}"
            try {
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                val errorMessage = " ${errorResponse.status} ${errorResponse.message}"
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error(errorBody, e)
            }
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}", e)
        }
    }
}