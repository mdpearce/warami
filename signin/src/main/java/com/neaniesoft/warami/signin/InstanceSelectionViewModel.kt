package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.repositories.instance.InstanceRepository
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import java.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Inject
@SignInScope
class InstanceSelectionViewModel(
    private val instanceRepository: InstanceRepository,
    private val instanceSettingsRepository: InstanceSettingsRepository,
    private val signinDestination: SignInScreenDestination,
) : ViewModel() {

    private val _instances: MutableStateFlow<List<InstanceDisplay>> = MutableStateFlow(emptyList())
    val instances: StateFlow<List<InstanceDisplay>> = _instances.asStateFlow()

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation: SharedFlow<Direction?> = _navigation.asSharedFlow()

    private val selectedInstanceBaseUrl: Flow<UriString> = instanceSettingsRepository.currentInstanceBaseUrl()

    init {
        viewModelScope.launch {
            instanceRepository.instances
                .collect { instances ->
                _instances.emit(
                    instances
                        .sortedByDescending { it.score }
                        .map {
                        InstanceDisplay(it.name, it.isOpen, it.iconUrl)
                    },
                )
            }

            selectedInstanceBaseUrl.collect { url ->
                if (url.value.isNotEmpty()) {
                    _navigation.emit(signinDestination)
                }
            }
        }
    }

    fun onInit() {
        onRefresh()
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            instanceRepository.fetchAndPopulateInstanceMetadata()
            _isRefreshing.emit(false)
        }
    }
}
