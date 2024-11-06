package com.scotiabank.githubapp.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scotiabank.githubapp.R

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CustomToolbar(
    title: String,
    shouldShowBackIcon: Boolean = false,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (shouldShowBackIcon) {
            Icon(

                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        onClick = onBackPressed,
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            bounded = false,
                            radius = 24.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Preview
@Composable
private fun ToolbarPreview() {
    CustomToolbar(title = "title") {
    }
}

