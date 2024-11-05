package com.scotiabank.githubapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UserProfileScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testUserProfileHappyPath() {

        composeTestRule.onNodeWithText("Enter GitHub Username").performTextInput("octocat")

        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("repoItem").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onAllNodesWithTag("repoItem")[0].assertExists()
        composeTestRule.onAllNodesWithTag("repoItem")[0].performClick()
        composeTestRule.onNodeWithText("Forks").assertExists()
        composeTestRule.onNodeWithText("Stars").assertExists()
    }
}