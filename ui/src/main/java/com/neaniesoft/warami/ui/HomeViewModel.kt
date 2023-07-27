package com.neaniesoft.warami.ui

import androidx.lifecycle.ViewModel
import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class HomeViewModel(
    private val isLoggedIn: IsLoggedInUseCase,
    private val homeNavigator: HomeNavigator,
) : ViewModel() {

    private val _navigation: MutableSharedFlow<DirectionDestinationSpec?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    suspend fun onInit() {
        if (isLoggedIn()) {
            _navigation.emit(homeNavigator.feedScreen())
        } else {
            _navigation.emit(homeNavigator.instanceSelectScreen())
        }
    }
}
