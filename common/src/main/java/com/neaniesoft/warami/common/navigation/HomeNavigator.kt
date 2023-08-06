package com.neaniesoft.warami.common.navigation

import com.neaniesoft.warami.common.models.CommunityId
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

interface HomeNavigator {
    fun feedScreen(communityId: CommunityId?): Direction
    fun instanceSelectScreen(): DirectionDestinationSpec
}
