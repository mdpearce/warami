package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.Instance
import com.neaniesoft.warami.data.repositories.instance.InstanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@SignInScope
class InstanceSelectionViewModel(private val instanceRepository: InstanceRepository) : ViewModel() {

    private val _instances: MutableStateFlow<List<Instance>> = MutableStateFlow(emptyList())
    val instances: StateFlow<List<Instance>> = _instances.asStateFlow()

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            instanceRepository.instances.collect { instances ->
                _instances.emit(instances)
            }
        }
    }

    suspend fun onInit() {
        instanceRepository.fetchAndPopulateInstanceMetadata()
    }
}
