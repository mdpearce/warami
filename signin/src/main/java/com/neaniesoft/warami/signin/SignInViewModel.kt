package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.RemoteResult
import com.neaniesoft.warami.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@SignInScope
class SignInViewModel(
    private val signIn: LoginUseCase,
) : ViewModel() {
    private val _screenState: MutableStateFlow<SignInScreenState> = MutableStateFlow(SignInScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    fun onDismissError() {
        viewModelScope.launch {
            if (screenState.value is SignInScreenState.Error) {
                _screenState.emit(SignInScreenState.Idle)
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
    object SigningIn: SignInScreenState()
    data class Error(val e: Exception) : SignInScreenState()
}
