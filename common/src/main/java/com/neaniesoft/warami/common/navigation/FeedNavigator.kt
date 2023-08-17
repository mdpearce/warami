package com.neaniesoft.warami.common.navigation

import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.UriString
import com.ramcosta.composedestinations.spec.Direction

interface FeedNavigator {
    fun commentsScreen(postId: PostId, parentCommentId: CommentId? = null): Direction

    fun feedScreenForCommunity(communityId: CommunityId): Direction

    fun fullScreenImage(imageUri: UriString): Direction
}
