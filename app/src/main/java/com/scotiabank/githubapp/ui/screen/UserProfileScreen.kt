package com.scotiabank.githubapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scotiabank.githubapp.R
import com.scotiabank.githubapp.network.Result
import com.scotiabank.githubapp.ui.component.CustomLoading
import com.scotiabank.githubapp.ui.component.CustomToolbar
import com.scotiabank.githubapp.ui.component.ErrorComponent
import com.scotiabank.githubapp.ui.component.ProfileHeader
import com.scotiabank.githubapp.ui.component.RepoItem
import com.scotiabank.githubapp.viewmodel.UserViewModel
import org.koin.androidx.compose.viewModel


@Composable
fun UserProfileScreen(
    onRepoClick: () -> Unit,
) {
    val userViewModel: UserViewModel by viewModel()
    val userResult by userViewModel.userData.collectAsState()
    val reposResult by userViewModel.reposData.collectAsState()
    val isLandscape = isLandscape()
    var username by remember { mutableStateOf("") }
    Column {
        CustomToolbar(title = stringResource(R.string.app_name_title)) {}

        Column(modifier = Modifier.padding(16.dp)) {
            // Search Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(R.string.enter_username)) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { userViewModel.fetchUserData(username) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(text = stringResource(R.string.search))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (userResult) {
                is Result.NotLoaded -> {}
                is Result.Loading -> CustomLoading()
                is Result.Success -> {
                    val user = (userResult as Result.Success).data
                    if (isLandscape) {
                        LazyColumn {
                            item {
                                ProfileHeader(user)
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            if (reposResult is Result.Success) {
                                val repos = (reposResult as Result.Success).data
                                items(repos) { repo ->
                                    RepoItem(
                                        repo = repo, onClick = {
                                            userViewModel.setSelectedRepo(repo)
                                            onRepoClick()
                                        },
                                        modifier = Modifier.testTag("repoItem")
                                    )
                                }
                            }
                        }
                    } else {
                        ProfileHeader(user)
                        Spacer(modifier = Modifier.height(16.dp))
                        if (reposResult is Result.Success) {
                            val repos = (reposResult as Result.Success).data
                            LazyColumn {
                                items(repos) { repo ->
                                    RepoItem(
                                        repo = repo, onClick = {
                                            userViewModel.setSelectedRepo(repo)
                                            onRepoClick()
                                        },
                                        modifier = Modifier.testTag("repoItem")
                                    )
                                }
                            }
                        }
                    }
                }

                is Result.Error -> ErrorComponent((userResult as Result.Error).message)
            }
        }
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
}