package com.neaniesoft.warami.signin

import com.neaniesoft.warami.common.models.UriString

data class InstanceDisplay(
    val displayName: String,
    val isPublic: Boolean,
    val icon: UriString?,
)
