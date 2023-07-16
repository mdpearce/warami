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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import me.tatarka.inject.annotations.Inject

typealias SignInScreen = @Composable () -> Unit

@Composable
@Destination
@Inject
fun SignInScreen(signInViewModel: () -> SignInViewModel) {
    val viewModel = viewModel {
        signInViewModel()
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenContent(signInScreenState: SignInScreenState, onSignInPressed: (username: String, password: String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarText by remember { mutableStateOf("") }

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

            Column(modifier = Modifier.padding(16.dp)) {
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


                when (signInScreenState) {
                    is SignInScreenState.Error -> {
                        snackbarText = signInScreenState.e.message ?: "Unknown error"
                        showSnackbar = true
                    }

                    is SignInScreenState.SigningIn -> {
                        // TODO show spinner or summit
                    }

                    is SignInScreenState.Idle -> {
                        // TODO hide the spinner or whatever it ends up being
                    }
                }

                if (showSnackbar) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        action = {
                            TextButton(onClick = { showSnackbar = false }) {
                                Text(stringResource(id = R.string.error_dismiss))
                            }
                        },
                    ) {
                        Text(snackbarText)
                    }
                }


            }

        }
    }

}

@Preview
@Composable
fun SignInScreenPreview() {
    Surface(Modifier.fillMaxSize()) {
        SignInScreenContent(signInScreenState = SignInScreenState.Idle, onSignInPressed = { _, _ -> })
    }
}
