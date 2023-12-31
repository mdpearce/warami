package com.neaniesoft.warami.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SignInScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()
    val instanceName by viewModel.instanceDisplayName.collectAsState()
    val navigation by viewModel.navigation.collectAsState(initial = null)

    LaunchedEffect(key1 = navigation) {
        navigation?.let { direction ->
            destinationsNavigator.navigate(direction)
        }
    }

    SignInScreenContent(
        instanceName = instanceName,
        signInScreenState = state,
        onSignInPressed = { username, password -> viewModel.onLogin(username, password) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenContent(
    instanceName: String,
    signInScreenState: SignInScreenState,
    onSignInPressed: (username: String, password: String) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                title = { Text(text = stringResource(id = R.string.sign_in_screen_title)) },
            )
        },
        bottomBar = {
            BottomAppBar() {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(
                        onClick = { onSignInPressed(username, password) },
                        enabled = (username.isNotEmpty() && password.isNotEmpty() && signInScreenState !is SignInScreenState.SigningIn),
                    ) {
                        Text(stringResource(id = R.string.button_sign_in))
                    }
                }
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.signing_in_to, instanceName),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(id = R.string.username_text_field_hint)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password_text_field_hint)) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (signInScreenState is SignInScreenState.Error) {
                val errorMessage = signInScreenState.e.message ?: stringResource(id = R.string.unknown_error)
                LaunchedEffect(key1 = Unit) {
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    Surface(Modifier.fillMaxSize()) {
        SignInScreenContent(instanceName = "Some instance", signInScreenState = SignInScreenState.Idle, onSignInPressed = { _, _ -> })
    }
}

@Preview
@Composable
fun SignInScreenErrorPreview() {
    Surface(Modifier.fillMaxSize()) {
        SignInScreenContent(
            instanceName = "Some instance",
            signInScreenState = SignInScreenState.Error(IllegalStateException("Could not find the thing")),
            onSignInPressed = { _, _ -> },
        )
    }
}
