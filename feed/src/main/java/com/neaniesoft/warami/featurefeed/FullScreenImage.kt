package com.neaniesoft.warami.featurefeed

import android.content.res.Resources
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun FullScreenImage(imageUri: UriString, navigator: DestinationsNavigator) {

    val anchors = mapOf(
        0f to SwipeAnchors.Initial,
        -screenHeight.toFloat() to SwipeAnchors.TopOffScreen,
        screenHeight.toFloat() to SwipeAnchors.BottomOffScreen,
    )

    val swipeableState: SwipeableState<SwipeAnchors> = rememberSwipeableState(initialValue = SwipeAnchors.Initial)

    Timber.d("CurrentState: ${swipeableState.currentValue}")

    if (swipeableState.currentValue is SwipeAnchors.TopOffScreen || swipeableState.currentValue is SwipeAnchors.BottomOffScreen) {
        navigator.popBackStack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Black,
    ) {
        Box(
            modifier = Modifier
                .offset(y = (swipeableState.offset.value / LocalDensity.current.density).dp)
                .swipeable(swipeableState, anchors, Orientation.Vertical, resistance = null),
        ) {
            Timber.d("offset: ${swipeableState.offset.value} and in dp: ${swipeableState.offset.value.dp}")
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(imageUri.value).build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
            ) // TODO add content description
        }
    }
}

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels

private sealed class SwipeAnchors {
    data object Initial : SwipeAnchors()
    data object TopOffScreen : SwipeAnchors()
    data object BottomOffScreen : SwipeAnchors()
}
