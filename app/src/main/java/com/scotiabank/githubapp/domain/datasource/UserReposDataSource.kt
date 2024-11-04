package com.scotiabank.githubapp.domain.datasource

import com.scotiabank.githubapp.domain.model.Repo

class UserReposDataSource {
    fun calculateTotalForks(repos: List<Repo>): Int {
        return repos.sumOf { it.forks }
    }

    fun shouldShowBadge(totalForks: Int): Boolean {
        return totalForks > 5000
    }
}