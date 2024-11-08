package com.scotiabank.githubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scotiabank.githubapp.ui.screen.RepoDetailScreen
import com.scotiabank.githubapp.ui.screen.UserProfileScreen

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "userProfile") {
                        composable("userProfile") {
                            UserProfileScreen(
                                onRepoClick = {
                                    navController.navigate("repoDetail")
                                }
                            )
                        }

                        composable("repoDetail") {
                            RepoDetailScreen(
                                onBackPressed = {
                                    navController.popBackStack()
                                })
                        }
                    }
                }
            }
        }
    }
}
