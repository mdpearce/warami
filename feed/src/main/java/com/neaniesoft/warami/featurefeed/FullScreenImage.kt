package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FullScreenImage(imageUri: UriString) {

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(imageUri.value).build(),
            contentDescription = "",
            contentScale = ContentScale.Fit,
        ) // TODO add content description
    }
}
