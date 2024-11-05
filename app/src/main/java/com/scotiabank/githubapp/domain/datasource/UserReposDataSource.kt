package com.scotiabank.githubapp.domain.datasource

import com.scotiabank.githubapp.domain.model.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserReposDataSource {

    private val _selectedRepoFlow = MutableStateFlow<Repo?>(null)
    val selectedRepoFlow: StateFlow<Repo?> = _selectedRepoFlow
    private val _hasBadge = MutableStateFlow(false)
    val hasBadge: StateFlow<Boolean> = _hasBadge

    private var totalForks: Int = 0


    fun updateTotalFork(forks: Int) {
        totalForks = forks
        _hasBadge.value = shouldShowBadge(totalForks)

    }

    fun setSelectedRepo(repo: Repo) {
        _selectedRepoFlow.value = repo
    }

    fun calculateTotalForks(repos: List<Repo>): Int {
        return repos.sumOf { it.forks }
    }

    fun shouldShowBadge(totalForks: Int): Boolean {
        return totalForks > 5000
    }
}