package com.neaniesoft.warami.data.repositories.post

import com.neaniesoft.warami.common.models.Post

sealed class PostListResult
data class PostList(val posts: List<Post>) : PostListResult()
object Fetching : PostListResult()
object Finished : PostListResult()
data class ErrorFetching(val exception: Exception) : PostListResult()
