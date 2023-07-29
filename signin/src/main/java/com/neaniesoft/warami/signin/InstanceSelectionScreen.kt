package com.neaniesoft.warami.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@RootNavGraph(start = true)
@Destination
fun InstanceSelectionScreen(
    navigator: DestinationsNavigator,
    viewModel: InstanceSelectionViewModel = hiltViewModel(),
) {
    val instances by viewModel.instances.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val navigation by viewModel.navigation.collectAsState(initial = null)
    val selectedRow by viewModel.selectedInstanceRow.collectAsState()
    val manualInstanceUrl by viewModel.manualInstanceUrl.collectAsState()

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
        viewModel::onManualInstanceEdited,
        manualInstanceUrl,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InstanceSelectionScreenContent(
    instances: List<InstanceDisplay>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    selectedRow: InstanceDisplay? = null,
    onRowSelected: (InstanceDisplay?) -> Unit,
    onSignInPressed: (InstanceDisplay?, String) -> Unit,
    onManualInstanceEdited: (String) -> Unit,
    manualInstanceUrl: String,
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)
    var hideKeyboard by remember { mutableStateOf(false) }

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
            BottomAppBar {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(
                        onClick = { onSignInPressed(selectedRow, manualInstanceUrl) },
                        enabled = (selectedRow != null || manualInstanceUrl.isNotEmpty()),
                    ) {
                        Text(stringResource(id = R.string.button_sign_in))
                    }
                }
            }
        },
    ) { it ->
        Column(
            modifier = Modifier
                .padding(it)
                .clickable { hideKeyboard = true },
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val focusManager = LocalFocusManager.current

            if (instances.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .padding(24.dp)
                        .weight(0.1f),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),

                ) {
                    LazyColumn {
                        items(instances.size) { index ->
                            val instance = instances[index]
                            val interactionSource = remember { MutableInteractionSource() }
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = rememberRipple(bounded = true),
                                        onClick = {
                                            onRowSelected(instance)
                                            onManualInstanceEdited("")
                                            focusManager.clearFocus()
                                        },
                                    )
                                    .focusable(true),
                                color = if (instance == selectedRow) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                                tonalElevation = if (instance == selectedRow) {
                                    16.dp
                                } else {
                                    0.dp
                                },
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

            TextField(
                value = manualInstanceUrl,
                onValueChange = {
                    onRowSelected(null)
                    onManualInstanceEdited(it)
                },
                label = { Text(text = stringResource(id = R.string.enter_instance_url)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .wrapContentHeight()
                    .focusable(true),
                prefix = { Text(text = stringResource(id = R.string.instance_prefix)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.clearFocus()
                    },
                ),
            )
            if (hideKeyboard) {
                focusManager.clearFocus()
                hideKeyboard = false
            }
        }

        PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState)
    }
}

@Preview
@Composable
fun InstanceSelectionScreenContentEmptyPreview() {
    val selectedElement = InstanceDisplay("Some other instance", "foo.bar", UriString("http://foo.bar"), null)

    InstanceSelectionScreenContent(
        isRefreshing = true,
        onRefresh = {},
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
        onSignInPressed = { _, _ -> },
        onManualInstanceEdited = {},
        manualInstanceUrl = "",
    )
}
