package com.scotiabank.githubapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.scotiabank.githubapp.R
import com.scotiabank.githubapp.domain.model.Repo
import com.scotiabank.githubapp.viewmodel.UserViewModel
import com.scotiabank.githubapp.network.Result
import com.scotiabank.githubapp.ui.component.CustomLoading
import com.scotiabank.githubapp.ui.component.ErrorComponent
import com.scotiabank.githubapp.ui.component.RepoItem
import org.koin.androidx.compose.viewModel


@Composable
fun UserProfileScreen(
    onRepoClick: () -> Unit,
) {
    val userViewModel: UserViewModel by viewModel()
    val userResult by userViewModel.userData.collectAsState()
    val reposResult by userViewModel.reposData.collectAsState()

    var username by remember { mutableStateOf("") }

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

        // Display user data or error message
        when (userResult) {
            is Result.NotLoaded -> {}
            is Result.Loading -> CustomLoading()
            is Result.Success -> {
                val user = (userResult as Result.Success).data
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = user.avatarUrl,
                            builder = {
                                placeholder(R.drawable.ic_git_placeholder)
                            }
                        ),
                        contentDescription = stringResource(R.string.user_avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(128.dp),
                    )
                    Text(
                        text = user.name ?: stringResource(R.string.no_name),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            is Result.Error -> ErrorComponent((userResult as Result.Error).message)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display repositories list
        if (reposResult is Result.Success) {
            val repos = (reposResult as Result.Success).data
            LazyColumn {
                items(repos) { repo ->
                    RepoItem(repo = repo, onClick = {
                        userViewModel.setSelectedRepo(repo)
                        onRepoClick()
                    })
                }
            }
        }
    }
}
