package com.scotiabank.githubapp.viewmodel

import androidx.lifecycle.ViewModel
import com.scotiabank.githubapp.domain.datasource.UserReposDataSource
import com.scotiabank.githubapp.domain.model.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepoDetailsViewModel(private val userReposDataSource: UserReposDataSource) : ViewModel() {
    private val _repoDetail = MutableStateFlow<Repo?>(null)
    val repoDetail: StateFlow<Repo?> = _repoDetail

    private val _showForkBadge = MutableStateFlow(false)
    val showForkBadge: StateFlow<Boolean> = _showForkBadge

    fun setRepoDetail(repo: Repo, totalForks: Int) {
        _repoDetail.value = repo
        _showForkBadge.value = userReposDataSource.shouldShowBadge(totalForks)
    }
}