package com.scotiabank.githubapp.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scotiabank.githubapp.R


@Composable
fun RepoDetailRow(
    @DrawableRes icon: Int,
    title: String,
    amount: String,
    modifier: Modifier = Modifier

) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = amount,
            )
        }
    }
}


@Preview
@Composable
private fun RepoDetailsRowPreview() {
    RepoDetailRow(icon = R.drawable.ic_fork, title = "Forks", amount = "1000")
}