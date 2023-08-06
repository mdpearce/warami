package com.neaniesoft.warami.ui.nav

import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.common.navigation.HomeNavigator
import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.featurefeed.destinations.CommentsScreenDestination
import com.neaniesoft.warami.featurefeed.destinations.FeedScreenDestination
import com.neaniesoft.warami.signin.destinations.InstanceSelectionScreenDestination
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

object WaramiNavigator : SignInNavigator, HomeNavigator, FeedNavigator {
    override fun signInScreen(): DirectionDestinationSpec {
        return SignInScreenDestination
    }

    override fun feedScreen(communityId: CommunityId?): Direction {
        return FeedScreenDestination(communityId)
    }

    override fun instanceSelectScreen(): DirectionDestinationSpec {
        return InstanceSelectionScreenDestination
    }

    override fun commentsScreen(postId: PostId, parentCommentId: CommentId?): Direction {
        return CommentsScreenDestination(postId, parentCommentId)
    }

    override fun feedScreenForCommunity(communityId: CommunityId): Direction {
        return FeedScreenDestination(communityId)
    }
}
