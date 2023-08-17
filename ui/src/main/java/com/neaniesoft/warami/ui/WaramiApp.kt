package com.neaniesoft.warami.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.neaniesoft.warami.common.extensions.findActivity
import com.neaniesoft.warami.common.extensions.setFullScreen
import com.neaniesoft.warami.featurefeed.destinations.FullScreenImageDestination
import com.neaniesoft.warami.ui.nav.RootNavGraph
import com.neaniesoft.warami.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import timber.log.Timber

@Composable
fun WaramiApp() {
    AppTheme {
        val navController = rememberNavController()

        val context = LocalContext.current
        val activity = remember { context.findActivity() }
        
        DisposableEffect(key1 = navController) {
            val listener = NavController.OnDestinationChangedListener { _, destination, arguments ->
                Timber.d("Destination route: ${destination.route}, and fullscreen destination = ${FullScreenImageDestination.route}")
                if (destination.route == FullScreenImageDestination.route) {
                    activity?.setFullScreen(true)
                } else {
                    activity?.setFullScreen(false)
                }
            }

            navController.addOnDestinationChangedListener(listener)

            onDispose {
                navController.removeOnDestinationChangedListener(listener)
            }
        }
        
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
                },
            ) {
            }
        }
    }
}

typealias WaramiApp = @Composable () -> Unit
