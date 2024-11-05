package com.scotiabank.githubapp

import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.domain.model.User

val mockRepo1 = Repo("repo1", "desc", 30, 100)
val mockRepo2 = Repo("repo1", "desc", 30, 200)
val mockRepo3 = Repo("repo1", "desc", 30, 500)
val mockRepo4 = Repo("repo1", "desc", 30, 6000)

val mockUser = User("testuser", "com.example/avatar")
