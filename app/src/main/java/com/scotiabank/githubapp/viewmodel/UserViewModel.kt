package com.scotiabank.githubapp.viewmodel

import com.scotiabank.githubapp.domain.model.Repo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.User
import com.scotiabank.githubapp.repository.UserRepository
import com.scotiabank.githubapp.network.Result
import com.scotiabank.githubapp.utils.DispatcherProvider

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val dataSource: UserReposDataSource,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _userData = MutableStateFlow<Result<User>>(Result.NotLoaded)
    val userData: StateFlow<Result<User>> = _userData

    private val _reposData = MutableStateFlow<Result<List<Repo>>>(Result.NotLoaded)
    val reposData: StateFlow<Result<List<Repo>>> = _reposData


    init {
        observeReposData()
    }

    fun fetchUserData(userId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _userData.value = Result.Loading
            _reposData.value = Result.Loading
            _userData.value = repository.getUser(userId)
            _reposData.value = repository.getUserRepos(userId)
        }
    }

    private fun observeReposData() {
        viewModelScope.launch {
            reposData.collect { result ->
                if (result is Result.Success) {
                    val totalForks = getTotalForks(result.data)
                    dataSource.updateTotalFork(totalForks)
                }
            }
        }
    }

    fun setSelectedRepo(repo: Repo) {
        dataSource.setSelectedRepo(repo)
    }


    private fun getTotalForks(repos: List<Repo>): Int {
        return dataSource.calculateTotalForks(repos)
    }
}
