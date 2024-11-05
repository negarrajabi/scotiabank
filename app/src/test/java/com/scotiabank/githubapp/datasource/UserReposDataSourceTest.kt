package com.scotiabank.githubapp.datasource

import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.mockRepo1
import com.scotiabank.githubapp.mockRepo2
import com.scotiabank.githubapp.mockRepo3
import com.scotiabank.githubapp.mockRepo4
import com.scotiabank.githubapp.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserReposDataSourceTest {

    private lateinit var userReposDataSource: UserReposDataSource

    @Before
    fun setup() {
        userReposDataSource = UserReposDataSource()
    }



    @Test
    fun `the result of getTotalForks should be sum of all the repositories forks`() = runTest {
        val repos = listOf(mockRepo1, mockRepo2, mockRepo3, mockRepo4)
        assertEquals(6800, userReposDataSource.calculateTotalForks(repos))
    }

    @Test
    fun `if the total forks is less than 500 then shouldShowBadge should be false`() = runTest {
        assertFalse(userReposDataSource.shouldShowBadge(10))
    }

    @Test
    fun `if the total forks is more than 500 then shouldShowBadge should be true`() = runTest {
        assertTrue(userReposDataSource.shouldShowBadge(6000))
    }

    @Test
    fun `if the total forks is exactly 500 then shouldShowBadge should be false`() = runTest {
        assertFalse(userReposDataSource.shouldShowBadge(5000))
    }


    @Test
    fun `when a repo selected, then selectedRepo flow should be updated`() = runTest {
        userReposDataSource.setSelectedRepo(mockRepo1)
        assertEquals(mockRepo1, userReposDataSource.selectedRepoFlow.value)
    }


    @Test
    fun `when total forks updated, hasBadge flow should get update`() = runTest {
        userReposDataSource.updateTotalFork(1000)
        assertFalse(userReposDataSource.hasBadge.value)

        userReposDataSource.updateTotalFork(6000)
        assertTrue(userReposDataSource.hasBadge.value)
    }
}