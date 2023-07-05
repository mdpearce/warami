package com.neaniesoft.warami.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.neaniesoft.warami.featurefeed.FeedScreen
import com.neaniesoft.warami.ui.theme.AppTheme

@Composable
fun WaramiApp() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // TODO: Tmp hard code to feed screen
            FeedScreen()
        }
    }
}
