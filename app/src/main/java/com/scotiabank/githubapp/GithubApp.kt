package com.scotiabank.githubapp

import android.app.Application
import com.scotiabank.githubapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubApp)
            modules(listOf(appModule))
        }
    }
}