package com.scotiabank.githubapp.viewmodel

import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepoDetailsViewModelTest {

    private lateinit var viewModel: RepoDetailsViewModel
    private lateinit var userReposDataSource: UserReposDataSource
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockRepo = Repo(name = "TestRepo", forks = 100, stars = 50, description = "")

    @Before
    fun setup() {
        userReposDataSource = UserReposDataSource()
        viewModel = RepoDetailsViewModel(userReposDataSource)
    }

    @Test
    fun `setRepoDetail sets repoDetail correctly`() = testScope.runTest {

        viewModel.setRepoDetail(mockRepo, totalForks = 2000)

        val repoDetail = viewModel.repoDetail.first()
        assertEquals(mockRepo, repoDetail)
    }

    @Test
    fun `setRepoDetail sets showForkBadge to true when totalForks is greater than 5000`() =
        testScope.runTest {
            viewModel.setRepoDetail(mockRepo, totalForks = 6000)

            val showForkBadge = viewModel.showForkBadge.first()
            TestCase.assertTrue(showForkBadge)
        }

    @Test
    fun `setRepoDetail sets showForkBadge to false when totalForks is 5000 or less`() =
        testScope.runTest {
            viewModel.setRepoDetail(mockRepo, totalForks = 5000)

            val showForkBadge = viewModel.showForkBadge.first()
            TestCase.assertFalse(showForkBadge)
        }
}