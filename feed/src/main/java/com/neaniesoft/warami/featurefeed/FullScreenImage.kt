package com.neaniesoft.warami.featurefeed

import android.content.res.Resources
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
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
            0f to INITIAL,
            -screenHeight.toFloat() to TOP_OFF_SCREEN,
            screenHeight.toFloat() to BOTTOM_OFF_SCREEN,
        )

        val swipeableState = rememberSwipeableState(initialValue = INITIAL)
        val zoomState = rememberZoomState()

        LaunchedEffect(key1 = swipeableState.isAnimationRunning) {
            if (swipeableState.isAnimationRunning) {
                val direction = when (swipeableState.direction) {
                    1f -> {
                        DIRECTION_DOWNWARDS
                    }

                    -1f -> {
                        DIRECTION_UPWARDS
                    }

                    else -> {
                        DIRECTION_STATIC
                    }
                }
                if ((direction == DIRECTION_UPWARDS && swipeableState.targetValue == TOP_OFF_SCREEN) ||
                    (direction == DIRECTION_DOWNWARDS && swipeableState.targetValue == BOTTOM_OFF_SCREEN)
                ) {
                    Timber.d("Fling off-screen started. Popping back-stack")
                    navigator.popBackStack()
                }
            }
        }

        val alpha = 1f - (abs(swipeableState.offset.value) / screenHeight.toFloat()).coerceIn(0f, 1f)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = (swipeableState.offset.value / LocalDensity.current.density).dp)
                .swipeable(
                    swipeableState,
                    anchors,
                    Orientation.Vertical,
                    resistance = null,
                    thresholds = { _, targetValue ->
                        val threshold: Float = when (targetValue) {
                            INITIAL -> 0.5f
                            TOP_OFF_SCREEN, BOTTOM_OFF_SCREEN -> {
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
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .zoomable(zoomState = zoomState, enableOneFingerZoom = true),
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(imageUri.value).build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alpha = alpha,
            ) // TODO add content description
        }
    }
}

private const val TOP_OFF_SCREEN = "TopOffScreen"
private const val BOTTOM_OFF_SCREEN = "BottomOffScreen"
private const val INITIAL = "Initial"

private const val DIRECTION_UPWARDS = "upwards"
private const val DIRECTION_DOWNWARDS = "downwards"
private const val DIRECTION_STATIC = "static"

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels
