package com.neaniesoft.warami.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import me.tatarka.inject.annotations.Inject

typealias InstanceSelectionScreen = @Composable () -> Unit

@Composable
@RootNavGraph(start = true)
@Destination
fun InstanceSelectionScreen(
    instanceSelectionViewModel: () -> InstanceSelectionViewModel,
    navigator: DestinationsNavigator,
    feedScreenDestination: Direction,
) {
    val viewModel = viewModel {
        instanceSelectionViewModel()
    }

    val instances by viewModel.instances.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        //viewModel.onInit()
        navigator.navigate(
            direction = feedScreenDestination,
            onlyIfResumed = true,
        )
    }
}
