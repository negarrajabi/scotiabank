package com.scotiabank.githubapp.datasource

import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.repository.UserRepository
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

    private val mockRepo1 = Repo("repo1", "desc", 30, 50)
    private val mockRepo2 = Repo("repo2", "desc", 30, 5000)
    private val mockRepo3 = Repo("repo3", "desc", 30, 100)
    private val mockRepo4 = Repo("repo4", "desc", 30, 20)

    @Test
    fun `the result of getTotalForks should be sum of all the repositories forks`() = runTest {
        val repos = listOf(mockRepo1, mockRepo2, mockRepo3, mockRepo4)
        assertEquals(5170, userReposDataSource.calculateTotalForks(repos))
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
}