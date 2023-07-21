package com.neaniesoft.warami.featurefeed.components.icons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object PostIcons {
    @Composable
    fun rememberModeComment(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "mode_comment",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(34.167f, 34.042f)
                    lineToRelative(-4.292f, -4.292f)
                    horizontalLineTo(6.25f)
                    quadToRelative(-1.042f, 0f, -1.833f, -0.792f)
                    quadToRelative(-0.792f, -0.791f, -0.792f, -1.833f)
                    verticalLineTo(6.208f)
                    quadToRelative(0f, -1.041f, 0.792f, -1.833f)
                    quadToRelative(0.791f, -0.792f, 1.833f, -0.792f)
                    horizontalLineToRelative(27.5f)
                    quadToRelative(1.083f, 0f, 1.854f, 0.792f)
                    quadToRelative(0.771f, 0.792f, 0.771f, 1.833f)
                    verticalLineToRelative(26.917f)
                    quadToRelative(0f, 0.833f, -0.792f, 1.187f)
                    quadToRelative(-0.791f, 0.355f, -1.416f, -0.27f)
                    close()
                    moveTo(6.25f, 6.208f)
                    verticalLineToRelative(20.917f)
                    horizontalLineTo(31f)
                    lineToRelative(2.75f, 2.75f)
                    verticalLineTo(6.208f)
                    horizontalLineTo(6.25f)
                    close()
                    moveToRelative(0f, 0f)
                    verticalLineToRelative(23.667f)
                    verticalLineTo(6.208f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberArrowUpward(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "arrow_upward",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(20f, 33.125f)
                    quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                    reflectiveQuadToRelative(-0.375f, -0.958f)
                    verticalLineTo(11.917f)
                    lineToRelative(-9.041f, 9f)
                    quadToRelative(-0.417f, 0.416f, -0.938f, 0.416f)
                    quadToRelative(-0.521f, 0f, -0.937f, -0.416f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.917f)
                    reflectiveQuadToRelative(0.375f, -0.917f)
                    lineTo(19.083f, 7.792f)
                    quadToRelative(0.209f, -0.209f, 0.438f, -0.292f)
                    quadToRelative(0.229f, -0.083f, 0.479f, -0.083f)
                    quadToRelative(0.25f, 0f, 0.479f, 0.083f)
                    quadToRelative(0.229f, 0.083f, 0.438f, 0.292f)
                    lineToRelative(11.291f, 11.291f)
                    quadToRelative(0.375f, 0.375f, 0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.375f, 0.917f)
                    quadToRelative(-0.416f, 0.416f, -0.937f, 0.416f)
                    quadToRelative(-0.521f, 0f, -0.938f, -0.416f)
                    lineToRelative(-9f, -9f)
                    verticalLineToRelative(19.875f)
                    quadToRelative(0f, 0.583f, -0.395f, 0.958f)
                    quadToRelative(-0.396f, 0.375f, -0.938f, 0.375f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberArrowDownward(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "arrow_downward",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(20f, 32.583f)
                    quadToRelative(-0.25f, 0f, -0.479f, -0.083f)
                    quadToRelative(-0.229f, -0.083f, -0.438f, -0.292f)
                    lineTo(7.792f, 20.917f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.917f)
                    reflectiveQuadToRelative(0.375f, -0.917f)
                    quadToRelative(0.416f, -0.416f, 0.958f, -0.416f)
                    reflectiveQuadToRelative(0.917f, 0.416f)
                    lineToRelative(9.041f, 9f)
                    verticalLineTo(8.167f)
                    quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                    reflectiveQuadTo(20f, 6.875f)
                    quadToRelative(0.542f, 0f, 0.938f, 0.375f)
                    quadToRelative(0.395f, 0.375f, 0.395f, 0.958f)
                    verticalLineToRelative(19.875f)
                    lineToRelative(9.042f, -9.041f)
                    quadToRelative(0.375f, -0.375f, 0.896f, -0.375f)
                    reflectiveQuadToRelative(0.937f, 0.416f)
                    quadToRelative(0.375f, 0.375f, 0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.375f, 0.917f)
                    lineTo(20.917f, 32.208f)
                    quadToRelative(-0.209f, 0.209f, -0.438f, 0.292f)
                    quadToRelative(-0.229f, 0.083f, -0.479f, 0.083f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberBookmark(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "bookmark",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(11.208f, 30.667f)
                    lineTo(20f, 26.917f)
                    lineToRelative(8.792f, 3.75f)
                    verticalLineTo(7.75f)
                    horizontalLineTo(11.208f)
                    close()
                    moveToRelative(-0.833f, 3.208f)
                    quadToRelative(-0.667f, 0.292f, -1.25f, -0.083f)
                    reflectiveQuadToRelative(-0.583f, -1.084f)
                    verticalLineTo(7.75f)
                    quadToRelative(0f, -1.083f, 0.791f, -1.854f)
                    quadToRelative(0.792f, -0.771f, 1.875f, -0.771f)
                    horizontalLineToRelative(17.584f)
                    quadToRelative(1.083f, 0f, 1.875f, 0.771f)
                    quadToRelative(0.791f, 0.771f, 0.791f, 1.854f)
                    verticalLineToRelative(24.958f)
                    quadToRelative(0f, 0.709f, -0.583f, 1.084f)
                    quadToRelative(-0.583f, 0.375f, -1.25f, 0.083f)
                    lineTo(20f, 29.792f)
                    close()
                    moveToRelative(0.833f, -26.125f)
                    horizontalLineToRelative(17.584f)
                    horizontalLineTo(20f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberPushPin(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "push_pin",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(26.333f, 20.917f)
                    lineToRelative(3.459f, 3.166f)
                    verticalLineToRelative(2.625f)
                    horizontalLineToRelative(-8.459f)
                    verticalLineToRelative(9.834f)
                    lineTo(20f, 37.875f)
                    lineToRelative(-1.292f, -1.333f)
                    verticalLineToRelative(-9.834f)
                    horizontalLineToRelative(-8.5f)
                    verticalLineToRelative(-2.625f)
                    lineToRelative(3.292f, -3.166f)
                    verticalLineTo(7.875f)
                    horizontalLineToRelative(-1.958f)
                    verticalLineTo(5.208f)
                    horizontalLineToRelative(16.75f)
                    verticalLineToRelative(2.667f)
                    horizontalLineToRelative(-1.959f)
                    close()
                    moveToRelative(-12.458f, 3.166f)
                    horizontalLineToRelative(12.083f)
                    lineToRelative(-2.25f, -2.125f)
                    verticalLineTo(7.875f)
                    horizontalLineToRelative(-7.583f)
                    verticalLineToRelative(14.083f)
                    close()
                    moveToRelative(6.042f, 0f)
                    close()
                }
            }.build()
        }
    }
}
