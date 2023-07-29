package com.neaniesoft.warami.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel()) {
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
