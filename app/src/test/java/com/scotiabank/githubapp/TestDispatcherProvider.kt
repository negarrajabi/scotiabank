package com.scotiabank.githubapp

import com.scotiabank.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher()
}
