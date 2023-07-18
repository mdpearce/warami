package com.neaniesoft.warami.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.neaniesoft.warami.featurefeed.FeedViewModel
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.neaniesoft.warami.signin.InstanceSelectionViewModel
import com.neaniesoft.warami.signin.SignInViewModel
import com.neaniesoft.warami.signin.destinations.InstanceSelectionScreenDestination
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import com.neaniesoft.warami.ui.destinations.HomeScreenDestination
import com.neaniesoft.warami.ui.nav.RootNavGraph
import com.neaniesoft.warami.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import me.tatarka.inject.annotations.Inject

@Inject
@Composable
fun WaramiApp(
    feedViewModel: () -> FeedViewModel,
    instanceSelectionViewModel: () -> InstanceSelectionViewModel,
    signInViewModel: () -> SignInViewModel,
    homeViewModel: () -> HomeViewModel,
) {
    AppTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = RootNavGraph,
                startRoute = UiNavGraph,
                modifier = Modifier.fillMaxSize(),
                dependenciesContainerBuilder = {
                    dependency {
                        destinationsNavigator
                    }
                    dependency(FeedScreenDestination) {
                        feedViewModel
                    }
                    dependency(InstanceSelectionScreenDestination) {
                        instanceSelectionViewModel
                    }
                    dependency(SignInScreenDestination) {
                        signInViewModel
                    }
                    dependency(HomeScreenDestination) {
                        homeViewModel
                    }
                },
            ) {
            }
        }
    }
}

typealias WaramiApp = @Composable () -> Unit
