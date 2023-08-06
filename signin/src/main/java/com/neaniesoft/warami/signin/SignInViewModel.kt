package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.domain.usecases.GetCurrentInstanceDisplayNameUseCase
import com.neaniesoft.warami.domain.usecases.LoginUseCase
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
@Inject
constructor(
    private val signIn: LoginUseCase,
    private val getCurrentInstanceDisplayName: GetCurrentInstanceDisplayNameUseCase,
    private val signInNavigator: SignInNavigator,
) : ViewModel() {
    private val _screenState: MutableStateFlow<SignInScreenState> = MutableStateFlow(SignInScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    private val _instanceDisplayName: MutableStateFlow<String> = MutableStateFlow("")
    val instanceDisplayName = _instanceDisplayName.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    init {
        viewModelScope.launch {
            getCurrentInstanceDisplayName().collect { displayName ->
                _instanceDisplayName.emit(displayName)
            }
        }
    }

    fun onLogin(username: String, password: String) {
        viewModelScope.launch {
            _screenState.emit(SignInScreenState.SigningIn)

            when (val result = signIn(username, password)) {
                is RemoteResult.Ok -> {
                    // Handle logged in
                    _screenState.emit(SignInScreenState.Idle)
                    _navigation.emit(signInNavigator.feedScreen(communityId = null))
                }

                is RemoteResult.Err -> {
                    _screenState.emit(SignInScreenState.Error(result.e))
                }
            }
        }
    }
}

sealed class SignInScreenState {
    object Idle : SignInScreenState()
    object SigningIn : SignInScreenState()
    data class Error(val e: Exception) : SignInScreenState()
}
