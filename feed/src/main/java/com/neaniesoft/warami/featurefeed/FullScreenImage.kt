package com.neaniesoft.warami.featurefeed

import android.content.res.Resources
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
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
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun FullScreenImage(imageUri: UriString, navigator: DestinationsNavigator) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Black,
    ) {
        val anchors = mapOf(
            0f to "Initial",
            -screenHeight.toFloat() to "TopOffScreen",
            screenHeight.toFloat() to "BottomOffScreen",
        )

        val swipeableState = rememberSwipeableState(initialValue = "Initial")

        Timber.d("CurrentState: ${swipeableState.currentValue}")

        if (swipeableState.currentValue == "TopOffScreen" || swipeableState.currentValue == "BottomOffScreen") {
            navigator.popBackStack()
        }

        val alpha = 1f - (abs(swipeableState.offset.value) / screenHeight.toFloat()).coerceIn(0f, 1f)

        Box(
            modifier = Modifier
                .offset(y = (swipeableState.offset.value / LocalDensity.current.density).dp)
                .swipeable(
                    swipeableState,
                    anchors,
                    Orientation.Vertical,
                    resistance = null,
                    thresholds = { _, targetValue ->
                        val threshold: Float = when (targetValue) {
                            "Initial" -> 0.5f
                            "TopOffScreen", "BottomOffScreen" -> {
                                if (abs(swipeableState.offset.value) >= screenHeight / 2) {
                                    0f
                                } else {
                                    1f
                                }
                            }

                            else -> 0.5f
                        }
                        FractionalThreshold(threshold)
                    },
                    velocityThreshold = 200.dp,
                ),
        ) {
            Timber.d("offset: ${swipeableState.offset.value} and in dp: ${swipeableState.offset.value.dp}")
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(imageUri.value).build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alpha = alpha,
            ) // TODO add content description
        }
    }
}

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels
