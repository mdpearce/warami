package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.repositories.instance.InstanceRepository
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import com.neaniesoft.warami.domain.usecases.ConstructInstanceBaseUrlUseCase
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@SignInScope
class InstanceSelectionViewModel(
    private val instanceRepository: InstanceRepository,
    private val instanceSettingsRepository: InstanceSettingsRepository,
    private val signinDestination: SignInScreenDestination,
    private val constructInstanceBaseUrl: ConstructInstanceBaseUrlUseCase,
) : ViewModel() {

    private val _instances: MutableStateFlow<List<InstanceDisplay>> = MutableStateFlow(emptyList())
    val instances: StateFlow<List<InstanceDisplay>> = _instances.asStateFlow()

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation: SharedFlow<Direction?> = _navigation.asSharedFlow()

    private val _selectedInstanceRow: MutableStateFlow<InstanceDisplay?> = MutableStateFlow(null)
    val selectedInstanceRow: StateFlow<InstanceDisplay?> = _selectedInstanceRow.asStateFlow()

    private val _manualInstanceUrl: MutableStateFlow<String> = MutableStateFlow("")
    val manualInstanceUrl = _manualInstanceUrl.asStateFlow()

    init {
        viewModelScope.launch {
            instanceRepository.instances
                .collect { instances ->
                    _instances.emit(
                        instances
                            .sortedByDescending { it.usage.users.activeMonth }
                            .map {
                                InstanceDisplay(
                                    displayName = it.name,
                                    baseUrl = it.baseUrl.value,
                                    apiBaseUrl = it.url,
                                    it.iconUrl,
                                )
                            },
                    )
                }
        }
    }

    fun onManualInstanceEdited(value: String) {
        viewModelScope.launch {
            _selectedInstanceRow.emit(null)
            _manualInstanceUrl.emit(value)
        }
    }

    fun onInit() {
        onRefresh()
    }

    fun selectRow(row: InstanceDisplay) {
        viewModelScope.launch {
            _selectedInstanceRow.emit(row)
            _manualInstanceUrl.emit("")
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            instanceRepository.fetchAndPopulateInstanceMetadata()
            _isRefreshing.emit(false)
        }
    }

    fun onSignInPressed(instanceDisplay: InstanceDisplay?, manualInstanceUrl: String) {
        viewModelScope.launch {
            if (manualInstanceUrl.isNotEmpty()) {
                val apiUrl = constructInstanceBaseUrl(manualInstanceUrl)
                instanceSettingsRepository.setInstance(manualInstanceUrl, apiUrl)
                _navigation.emit(signinDestination)
            } else if (instanceDisplay != null) {
                instanceSettingsRepository.setInstance(instanceDisplay.displayName, instanceDisplay.apiBaseUrl)
                _navigation.emit(signinDestination)
            }

        }
    }
}
