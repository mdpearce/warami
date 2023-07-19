package com.neaniesoft.warami.common

sealed class RemoteResult<T> {
    data class Ok<T>(val value: T) : RemoteResult<T>()
    data class Err<T>(val e: Exception) : RemoteResult<T>()
}
