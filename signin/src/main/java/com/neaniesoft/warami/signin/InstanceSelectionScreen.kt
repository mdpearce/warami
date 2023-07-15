package com.neaniesoft.warami.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

typealias InstanceSelectionScreen = @Composable () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RootNavGraph(start = true)
@Destination
fun InstanceSelectionScreen(
    instanceSelectionViewModel: () -> InstanceSelectionViewModel,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel {
        instanceSelectionViewModel()
    }

    val instances by viewModel.instances.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val navigation by viewModel.navigation.collectAsState(initial = null)

    LaunchedEffect(key1 = navigation) {
        navigation?.let { navigator.navigate(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.onInit()
    }

    InstanceSelectionScreenContent(instances, isRefreshing, viewModel::onRefresh)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InstanceSelectionScreenContent(instances: List<InstanceDisplay>, isRefreshing: Boolean, onRefresh: () -> Unit) {
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = { Text(text = stringResource(id = R.string.instance_selection_screen_title)) },
            )
        },
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (instances.isNotEmpty()) {

                Surface(modifier = Modifier.padding(24.dp), shape = MaterialTheme.shapes.medium, elevation = 4.dp) {

                    LazyColumn {
                        items(instances.size) { index ->
                            val instance = instances[index]
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                Text(text = instance.displayName)
                            }
                        }
                    }
                }
            }
            
            PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState)


        }
    }
}

@Preview
@Composable
fun InstanceSelectionScreenContentEmptyPreview() {
    InstanceSelectionScreenContent(
        isRefreshing = true, onRefresh = {},
        instances = listOf(
            InstanceDisplay("Some instance", true, null),
            InstanceDisplay("Some other instance", true, null),
            InstanceDisplay("Some owiurjgf instance", true, null),
            InstanceDisplay("Some rg instance", true, null),
            InstanceDisplay("Some wef finstance", true, null),
            InstanceDisplay("Some wef instance", true, null),
            InstanceDisplay("Some wefew instance", true, null),
            InstanceDisplay("Some wef winstance", true, null),
            InstanceDisplay("Some wef wefinstance", true, null),
            InstanceDisplay("Some fwinstance", true, null),
            InstanceDisplay("Some fggert geinstance", true, null),
            InstanceDisplay("Sometg etg et instance", true, null),
            InstanceDisplay("Some instance", true, null),
            InstanceDisplay("Somehrethr th instance", true, null),
            InstanceDisplay("Some rth  instance", true, null),
            InstanceDisplay("Somerthrt hrt h instance", true, null),
            InstanceDisplay("Some rth rtinstance", true, null),
            InstanceDisplay("Some   instance", true, null),
        ),
    )
}

data class InstanceDisplay(
    val displayName: String,
    val isPublic: Boolean,
    val icon: UriString?,
)
