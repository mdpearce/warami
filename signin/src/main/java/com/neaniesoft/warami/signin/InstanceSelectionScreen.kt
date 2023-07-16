package com.neaniesoft.warami.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val selectedRow by viewModel.selectedInstanceRow.collectAsState()

    LaunchedEffect(key1 = navigation) {
        navigation?.let { navigator.navigate(it) }
    }

    LaunchedEffect(Unit) {
        viewModel.onInit()
    }

    InstanceSelectionScreenContent(
        instances,
        isRefreshing,
        viewModel::onRefresh,
        selectedRow,
        viewModel::selectRow,
        viewModel::onSignInPressed,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InstanceSelectionScreenContent(
    instances: List<InstanceDisplay>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    selectedRow: InstanceDisplay? = null,
    onRowSelected: (InstanceDisplay) -> Unit,
    onSignInPressed: (InstanceDisplay?) -> Unit,
) {
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
        bottomBar = {
            BottomAppBar() {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(onClick = { onSignInPressed(selectedRow) }, enabled = selectedRow != null) {
                        Text(stringResource(id = R.string.button_sign_in))
                    }
                }
            }
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
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(bounded = true),
                                        onClick = { onRowSelected(instance) },
                                    )
                                    .background(
                                        if (instance == selectedRow) {
                                            MaterialTheme.colorScheme.secondaryContainer
                                        } else {
                                            Color.Transparent
                                        },
                                    ),
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Text(
                                        modifier = Modifier.alignByBaseline(),
                                        text = instance.displayName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (instance == selectedRow) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        },
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .alignByBaseline(),
                                        text = instance.baseUrl,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (instance == selectedRow) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        },
                                    )
                                }
                            }
                            Divider()
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

    val selectedElement = InstanceDisplay("Some other instance", "foo.bar", UriString("http://foo.bar"), null)

    InstanceSelectionScreenContent(
        isRefreshing = true, onRefresh = {},
        instances = listOf(
            InstanceDisplay("Some instance", "foo.bar", UriString("http://foo.bar"), null),
            selectedElement,
            InstanceDisplay("Some wefefinstance", "foo.bar", UriString("http://foo.bar"), null),

            InstanceDisplay("Some rg instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some wef finstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some wef instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some wefew instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some wef winstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some wef wefinstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some fwinstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some fggert geinstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Sometg etg et instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Somehrethr th instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some rth  instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Somerthrt hrt h instance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some rth rtinstance", "foo.bar", UriString("http://foo.bar"), null),
            InstanceDisplay("Some   instance", "foo.bar", UriString("http://foo.bar"), null),
        ),
        selectedRow = selectedElement,
        onRowSelected = {},
        onSignInPressed = {},
    )
}
