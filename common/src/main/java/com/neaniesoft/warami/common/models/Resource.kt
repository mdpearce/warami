package com.neaniesoft.warami.common.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Idle<T> : Resource<T>()
}

fun <T> Flow<Resource<T>>.split(
    itemsFlow: MutableStateFlow<T>,
    stateFlow: MutableStateFlow<Resource<T>>,
) = onEach { resource ->
    when (resource) {
        is Resource.Success -> {
            itemsFlow.value = resource.data
            stateFlow.value = Resource.Idle()
        }

        else -> stateFlow.value = resource
    }
}
