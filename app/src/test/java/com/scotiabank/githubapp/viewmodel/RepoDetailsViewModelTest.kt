package com.scotiabank.githubapp.viewmodel

import com.scotiabank.githubapp.TestDispatcherProvider
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.mockRepo2
import com.scotiabank.githubapp.mockRepo4
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {

    private lateinit var viewModel: RepoDetailsViewModel
    private lateinit var userReposDataSource: UserReposDataSource

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatcherProvider = TestDispatcherProvider()
    private val selectedRepoFlow = MutableStateFlow<Repo?>(null)
    private val hasBadgeFlow = MutableStateFlow(false)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userReposDataSource = mockk {
            every { selectedRepoFlow } returns this@RepoDetailsViewModelTest.selectedRepoFlow
            every { hasBadge } returns this@RepoDetailsViewModelTest.hasBadgeFlow
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observeRepo updates repoDetail and showForkBadge`() = runTest(testDispatcher) {
        viewModel = RepoDetailsViewModel(userReposDataSource, dispatcherProvider)
        hasBadgeFlow.update { true }
        selectedRepoFlow.update { mockRepo4 }

        advanceUntilIdle()
        assertEquals(mockRepo4, viewModel.repoDetail.value)
        assertEquals(true, viewModel.showForkBadge.value)
    }

    @Test
    fun `observeRepo updates repoDetail when selectedRepoFlow changes`() = runTest(testDispatcher) {
        viewModel = RepoDetailsViewModel(userReposDataSource, dispatcherProvider)
        selectedRepoFlow.update { mockRepo2 }

        advanceUntilIdle()
        assertEquals(mockRepo2, viewModel.repoDetail.value)
    }

}
