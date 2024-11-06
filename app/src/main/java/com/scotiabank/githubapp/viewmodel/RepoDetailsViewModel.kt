package com.scotiabank.githubapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoDetailsViewModel(
    private val userReposDataSource: UserReposDataSource,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _repoDetail = MutableStateFlow<Repo?>(null)
    val repoDetail: StateFlow<Repo?> = _repoDetail

    private val _showForkBadge = MutableStateFlow(false)
    val showForkBadge: StateFlow<Boolean> = _showForkBadge

    init {
        observeRepo()
    }

    private fun observeRepo() {
        viewModelScope.launch(dispatcherProvider.io) {
            userReposDataSource.selectedRepoFlow.collect {
                _repoDetail.value = it
                _showForkBadge.value = userReposDataSource.hasBadge.value
            }
        }
    }
}