package com.scotiabank.githubapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoDetailsViewModel(private val userReposDataSource: UserReposDataSource) : ViewModel() {
    private val _repoDetail = MutableStateFlow<Repo?>(null)
    val repoDetail: StateFlow<Repo?> = _repoDetail

    private val _showForkBadge = MutableStateFlow(false)
    val showForkBadge: StateFlow<Boolean> = _showForkBadge

    init {
        observeRepo()
    }

    private fun observeRepo() {
        viewModelScope.launch {
            userReposDataSource.selectedRepoFlow.collect {
                _repoDetail.value = it
                _showForkBadge.value = userReposDataSource.hasBadge.value
            }
        }
    }
}