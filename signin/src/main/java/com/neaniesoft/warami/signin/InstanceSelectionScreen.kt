package com.neaniesoft.warami.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import me.tatarka.inject.annotations.Inject

typealias InstanceSelectionScreen = @Composable () -> Unit

@Composable
@RootNavGraph(start = true)
@Destination
@Inject
fun InstanceSelectionScreen(instanceSelectionViewModel: () -> InstanceSelectionViewModel) {
    val viewModel = viewModel {
        instanceSelectionViewModel()
    }

    val instances by viewModel.instances.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onInit()
    }
}
