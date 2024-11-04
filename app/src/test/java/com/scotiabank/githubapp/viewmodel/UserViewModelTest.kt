package com.scotiabank.githubapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.scotiabank.githubapp.TestDispatcherProvider
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.network.Result

import com.scotiabank.githubapp.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        repository = mockk()
        dataSource = mockk()
        viewModel = UserViewModel(repository, dataSource, dispatcherProvider)
    }

    @Test
    fun `fetchUserData updates userData with Success when repository call is successful`() =
        runTest(testDispatcher) {
            val mockUser = User(name = "TestUser", avatarUrl = "https://example.com/avatar")
            coEvery { repository.getUser("testuser") } returns Result.Success(mockUser)
            coEvery { repository.getUserRepos("testuser") } returns Result.Success(emptyList())

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
            val mockUser = User(name = "TestUser", avatarUrl = "https://example.com/avatar")
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
}
