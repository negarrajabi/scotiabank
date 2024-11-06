package com.scotiabank.githubapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scotiabank.githubapp.ui.component.CustomToolbar
import com.scotiabank.githubapp.ui.component.RepoDetailRow
import com.scotiabank.githubapp.R
import com.scotiabank.githubapp.viewmodel.RepoDetailsViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun RepoDetailScreen(
    onBackPressed: () -> Unit
) {
    val viewModel: RepoDetailsViewModel by viewModel()
    val repo by viewModel.repoDetail.collectAsState()
    val showBadge by viewModel.showForkBadge.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),

        ) {
        CustomToolbar(title = repo?.name ?: "", shouldShowBackIcon = true) {
            onBackPressed()
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            repo?.let {
                if (showBadge) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_golden_user),
                        contentDescription = "Badge Icon",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(80.dp)

                    )
                    Text(
                        text = stringResource(R.string.githubber),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = it.description ?: stringResource(R.string.no_description))
                RepoDetailRow(
                    icon = R.drawable.ic_fork,
                    title = stringResource(R.string.forks),
                    amount = it.forks.toString()
                )
                RepoDetailRow(
                    icon = R.drawable.ic_star,
                    title = stringResource(R.string.stars),
                    amount = it.stars.toString()
                )
            }
        }
    }
}
