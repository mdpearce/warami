package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neaniesoft.warami.featurefeed.components.PostCard
import com.neaniesoft.warami.featurefeed.models.EmptyFeed
import com.neaniesoft.warami.featurefeed.models.ErrorFeed
import com.neaniesoft.warami.featurefeed.models.PostFeed


@Composable
fun FeedScreen(viewModel: FeedViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.onRefresh()
    }

    val feedList = viewModel.feedList.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (val feedListValue = feedList.value) {
            is EmptyFeed -> item { }
            is ErrorFeed -> item {
                Text(
                    text = feedListValue.e.localizedMessage ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is PostFeed -> items(items = feedListValue.posts) { post ->
                PostCard(
                    communityName = post.community.name,
                    creatorName = post.creator.name,
                    postedTime = "12h",
                    communityThumbnailUri = post.thumbnail,
                    postTitle = post.name,
                    postThumbnailUri = post.thumbnail,
                    postUri = post.url
                )
            }
        }
    }
}
