package com.neaniesoft.warami.ui.nav

import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.featurefeed.destinations.CommentsScreenDestination
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.neaniesoft.warami.signin.destinations.InstanceSelectionScreenDestination
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

object WaramiNavigator : SignInNavigator, HomeNavigator, FeedNavigator {
    override fun signInScreen(): DirectionDestinationSpec {
        return SignInScreenDestination
    }

    override fun feedScreen(): DirectionDestinationSpec {
        return FeedScreenDestination
    }

    override fun instanceSelectScreen(): DirectionDestinationSpec {
        return InstanceSelectionScreenDestination
    }

    override fun commentsScreen(): DirectionDestinationSpec {
        return CommentsScreenDestination
    }
}
