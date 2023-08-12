package com.neaniesoft.warami.common.navigation

import com.neaniesoft.warami.common.models.CommunityId
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

interface HomeNavigator {
    fun homeFeedScreen(): Direction
    fun instanceSelectScreen(): DirectionDestinationSpec
}
