package com.scotiabank.githubapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scotiabank.githubapp.R
import com.scotiabank.githubapp.domain.model.Repo


@Composable
fun RepoItem(
    repo: Repo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = repo.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = repo.description ?: stringResource(R.string.no_description),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview
@Composable
private fun RepoItemPreview() {
    RepoItem(repo = Repo("title", "description", 100, 200)) {

    }
}