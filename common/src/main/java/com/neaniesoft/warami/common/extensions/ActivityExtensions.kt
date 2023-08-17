package com.neaniesoft.warami.common.extensions

import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.setFullScreen(isFullScreen: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, !isFullScreen)
    val insetsController = WindowInsetsControllerCompat(window, window.decorView)

    if (isFullScreen) {
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        insetsController.show(WindowInsetsCompat.Type.systemBars())
    }
}
