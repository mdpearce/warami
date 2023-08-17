package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.extensions.findActivity
import com.neaniesoft.warami.common.extensions.setFullScreen
import com.neaniesoft.warami.common.models.UriString
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FullScreenImage(imageUri: UriString) {
    val context = LocalContext.current
    val activity = context.findActivity()

    var isFullScreen by remember { mutableStateOf(true) }

    activity?.setFullScreen(isFullScreen)

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(imageUri.value).build(),
            contentDescription = "",
        ) // TODO add content description
    }
}
