package com.scotiabank.githubapp.repository

import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.dto.UserDto
import com.scotiabank.githubapp.network.GithubApi
import com.scotiabank.githubapp.network.Result

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private lateinit var api: GithubApi

    private val mockUser = User(name = "TestUser", avatarUrl = "https://example.com/avatar")
    private val mockUserDto = UserDto(name = "TestUser", avatarUrl = "https://example.com/avatar")


    @Before
    fun setup() {
        api = mockk()
        repository = UserRepository(api)
    }

    @Test
    fun `when API call is successful then getUser returns Success`() = runTest {
        coEvery { api.getUser("testuser") } returns Response.success(mockUserDto)
        val result = repository.getUser("testuser")
        assert(result is Result.Success)
        assertEquals(mockUser, (result as Result.Success).data)
    }

    @Test
    fun `when API call returns 404 then getUser returns Error `() = runTest {
        coEvery { api.getUser("unknownuser") } returns Response.error(
            404,
            ResponseBody.create(null, "")
        )
        val result = repository.getUser("unknownuser")
        assert(result is Result.Error)
    }
}