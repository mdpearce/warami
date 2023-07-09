package com.neaniesoft.warami.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.neaniesoft.warami.featurefeed.FeaturefeedNavGraph
import com.neaniesoft.warami.featurefeed.FeedViewModel
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.neaniesoft.warami.ui.nav.RootNavGraph
import com.neaniesoft.warami.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import me.tatarka.inject.annotations.Inject

@Inject
@Composable
fun WaramiApp(feedViewModel: () -> FeedViewModel) {
    AppTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = RootNavGraph,
                startRoute = FeaturefeedNavGraph,
                modifier = Modifier.fillMaxSize(),
                dependenciesContainerBuilder = {
                    dependency(FeedScreenDestination) {
                        feedViewModel
                    }
                },
            ) {
            }
        }
    }
}

typealias WaramiApp = @Composable () -> Unit
