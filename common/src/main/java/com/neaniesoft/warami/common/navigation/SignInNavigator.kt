package com.neaniesoft.warami.common.navigation

import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

interface SignInNavigator {
    fun signInScreen(): DirectionDestinationSpec
    fun homeFeedScreen(): Direction
}
