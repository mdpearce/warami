package com.neaniesoft.warami.ui.nav

import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

object WaramiNavigator : SignInNavigator {
    override fun feedScreen(): DirectionDestinationSpec {
        return FeedScreenDestination
    }
}
