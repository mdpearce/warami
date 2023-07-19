package com.neaniesoft.warami.ui.nav

import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.neaniesoft.warami.signin.destinations.InstanceSelectionScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

object WaramiNavigator : SignInNavigator, HomeNavigator {
    override fun feedScreen(): DirectionDestinationSpec {
        return FeedScreenDestination
    }

    override fun instanceSelectScreen(): DirectionDestinationSpec {
        return InstanceSelectionScreenDestination
    }
}
