package com.neaniesoft.warami.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModelProvider: () -> HomeViewModel) {
    val viewModel = viewModelProvider()

    val navigationDestination by viewModel.navigation.collectAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        viewModel.onInit()
    }

    LaunchedEffect(key1 = navigationDestination) {
        navigationDestination?.let { destination ->
            navigator.navigate(destination)
        }
    }
}
