package com.scotiabank.githubapp.di

import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.network.GithubApi
import com.scotiabank.githubapp.repository.UserRepository
import com.scotiabank.githubapp.viewmodel.RepoDetailsViewModel
import com.scotiabank.githubapp.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
    single { UserRepository(get()) }
    factory { UserReposDataSource() }

    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { RepoDetailsViewModel(get()) }
}




private fun provideRetrofit(): Retrofit {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging) // Add the logging interceptor
        .build()

    return Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideApiService(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}