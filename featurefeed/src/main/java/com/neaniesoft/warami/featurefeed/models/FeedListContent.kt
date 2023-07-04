package com.neaniesoft.warami.featurefeed.models

import com.neaniesoft.warami.common.models.Post

sealed class FeedListContent
data class PostFeed(val posts: List<Post>) : FeedListContent()
object EmptyFeed : FeedListContent()
data class ErrorFeed(val e: Exception) : FeedListContent()
