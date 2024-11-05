package com.scotiabank.githubapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.scotiabank.githubapp.TestDispatcherProvider
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.mockRepo1
import com.scotiabank.githubapp.mockRepo2
import com.scotiabank.githubapp.mockRepo3
import com.scotiabank.githubapp.mockUser
import com.scotiabank.githubapp.network.Result

import com.scotiabank.githubapp.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.times

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserViewModel
    private lateinit var repository: UserRepository
    private lateinit var dataSource: UserReposDataSource
    private val dispatcherProvider = TestDispatcherProvider()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for testing
        repository = mockk()
        dataSource = mockk()
        viewModel = UserViewModel(repository, dataSource, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset Main dispatcher after tests
    }

    @Test
    fun `fetchUserData updates userData with Success when repository call is successful`() =
        runTest(testDispatcher) {
            val repos = emptyList<Repo>()
            coEvery { repository.getUser("testuser") } returns Result.Success(mockUser)
            coEvery { repository.getUserRepos("testuser") } returns Result.Success(repos)
            coEvery { dataSource.calculateTotalForks(repos) } returns 0
            coEvery { dataSource.updateTotalFork(0) } returns Unit


            viewModel.fetchUserData("testuser")
            advanceUntilIdle()

            assert(viewModel.userData.first() is Result.Success)
            assertEquals(mockUser, (viewModel.userData.first() as Result.Success).data)
        }

    @Test
    fun `fetchUserData updates userData with Error when repository call fails`() =
        runTest(testDispatcher) {
            coEvery { repository.getUser("unknownuser") } returns Result.Error("User not found")
            coEvery { repository.getUserRepos("unknownuser") } returns Result.Error("Repository fetch error")

            viewModel.fetchUserData("unknownuser")
            advanceUntilIdle()

            assert(viewModel.userData.first() is Result.Error)
            assertEquals("User not found", (viewModel.userData.first() as Result.Error).message)
        }


    @Test
    fun `fetchUserData updates reposData with Error when repository repos call fails`() =
        runTest(testDispatcher) {
            coEvery { repository.getUser("testuser") } returns Result.Success(mockUser)
            coEvery { repository.getUserRepos("testuser") } returns Result.Error("Repository fetch error")

            viewModel.fetchUserData("testuser")
            advanceUntilIdle()

            assert(viewModel.reposData.first() is Result.Error)
            assertEquals(
                "Repository fetch error",
                (viewModel.reposData.first() as Result.Error).message
            )
        }

    @Test
    fun `when user repositories fetched successfully then totalForks should get updated`() =
        runTest(testDispatcher) {
            val repos = listOf(mockRepo1, mockRepo2, mockRepo3)
            coEvery { repository.getUser("testuser") } returns Result.Success(mockUser)
            coEvery { repository.getUserRepos("testuser") } returns Result.Success(repos)
            coEvery { dataSource.calculateTotalForks(repos) } returns 800
            coEvery { dataSource.updateTotalFork(any()) } returns Unit


            viewModel.fetchUserData("testuser")
            advanceUntilIdle()
            coVerify(exactly = 1) { dataSource.updateTotalFork(any()) }


        }
}
