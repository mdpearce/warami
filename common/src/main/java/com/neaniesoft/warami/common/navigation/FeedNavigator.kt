package com.neaniesoft.warami.common.navigation

import com.neaniesoft.warami.common.models.PostId
import com.ramcosta.composedestinations.spec.Direction

interface FeedNavigator {
    fun commentsScreen(postId: PostId): Direction
}
