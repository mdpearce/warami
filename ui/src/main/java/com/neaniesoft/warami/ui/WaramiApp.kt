package com.neaniesoft.warami.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.neaniesoft.warami.ui.nav.RootNavGraph
import com.neaniesoft.warami.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

@Composable
fun WaramiApp() {
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
                },
            ) {
            }
        }
    }
}

typealias WaramiApp = @Composable () -> Unit
