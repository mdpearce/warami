package com.neaniesoft.warami.featurefeed

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CommentsScreen(
    postId: PostId,
    commentsViewModel: () -> CommentsViewModel,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel {
        commentsViewModel()
    }

    Log.d("CommentsScreen", "PostId: $postId")
    LaunchedEffect(key1 = postId) {
        Log.d("CommentsScreen", "refreshing $postId")
        viewModel.initialFetch(postId)
    }

    val comments by viewModel.comments.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(comments.size) {
                val comment = comments[it]
                Text(text = comment.content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}
