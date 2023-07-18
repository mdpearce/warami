package com.neaniesoft.warami.common.navigation

import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

interface HomeNavigator {
    fun feedScreen(): DirectionDestinationSpec
    fun instanceSelectScreen(): DirectionDestinationSpec
}
