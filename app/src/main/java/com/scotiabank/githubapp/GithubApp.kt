package com.scotiabank.githubapp

import android.app.Application
import com.scotiabank.githubapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@GithubApp)
            modules(listOf(networkModule))
        }
    }
}