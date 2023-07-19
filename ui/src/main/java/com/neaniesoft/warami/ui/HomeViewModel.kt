package com.neaniesoft.warami.ui

import androidx.lifecycle.ViewModel
import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.neaniesoft.warami.ui.di.UiScope
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.tatarka.inject.annotations.Inject

@Inject
@UiScope
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
