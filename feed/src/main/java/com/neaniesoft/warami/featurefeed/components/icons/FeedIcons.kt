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

object FeedIcons {
    @Composable
    fun rememberFilterList(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "filter_list",
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
                    moveTo(18.083f, 29.333f)
                    quadToRelative(-0.583f, 0f, -0.958f, -0.395f)
                    quadToRelative(-0.375f, -0.396f, -0.375f, -0.938f)
                    quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                    quadToRelative(0.375f, -0.395f, 0.958f, -0.395f)
                    horizontalLineToRelative(3.834f)
                    quadToRelative(0.541f, 0f, 0.937f, 0.395f)
                    quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                    quadToRelative(0f, 0.583f, -0.396f, 0.958f)
                    reflectiveQuadToRelative(-0.937f, 0.375f)
                    close()
                    moveTo(6.5f, 12.792f)
                    quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                    reflectiveQuadToRelative(-0.375f, -0.959f)
                    quadToRelative(0f, -0.541f, 0.375f, -0.937f)
                    reflectiveQuadToRelative(0.917f, -0.396f)
                    horizontalLineToRelative(26.958f)
                    quadToRelative(0.584f, 0f, 0.959f, 0.396f)
                    reflectiveQuadToRelative(0.375f, 0.937f)
                    quadToRelative(0f, 0.584f, -0.375f, 0.959f)
                    reflectiveQuadToRelative(-0.959f, 0.375f)
                    close()
                    moveToRelative(4.958f, 8.25f)
                    quadToRelative(-0.541f, 0f, -0.937f, -0.375f)
                    reflectiveQuadToRelative(-0.396f, -0.959f)
                    quadToRelative(0f, -0.541f, 0.396f, -0.916f)
                    reflectiveQuadToRelative(0.937f, -0.375f)
                    horizontalLineToRelative(17.084f)
                    quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                    quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                    quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.916f, 0.375f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberAllInclusive(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "all_inclusive",
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
                    moveTo(9.042f, 29f)
                    quadToRelative(-3.709f, 0f, -6.23f, -2.667f)
                    quadToRelative(-2.52f, -2.666f, -2.52f, -6.375f)
                    quadToRelative(0f, -3.75f, 2.541f, -6.375f)
                    quadToRelative(2.542f, -2.625f, 6.209f, -2.625f)
                    quadToRelative(1.541f, 0f, 2.916f, 0.5f)
                    reflectiveQuadToRelative(2.5f, 1.542f)
                    lineToRelative(3.584f, 3.417f)
                    lineToRelative(-1.875f, 1.791f)
                    lineToRelative(-3.417f, -3.25f)
                    quadToRelative(-0.75f, -0.708f, -1.708f, -1.02f)
                    quadToRelative(-0.959f, -0.313f, -2f, -0.313f)
                    quadToRelative(-2.584f, 0f, -4.354f, 1.854f)
                    quadToRelative(-1.771f, 1.854f, -1.771f, 4.479f)
                    reflectiveQuadToRelative(1.771f, 4.521f)
                    quadToRelative(1.77f, 1.896f, 4.354f, 1.896f)
                    quadToRelative(1f, 0f, 1.958f, -0.333f)
                    quadToRelative(0.958f, -0.334f, 1.708f, -1f)
                    lineTo(25.625f, 13f)
                    quadToRelative(1.083f, -1.042f, 2.458f, -1.542f)
                    quadToRelative(1.375f, -0.5f, 2.834f, -0.5f)
                    quadToRelative(3.708f, 0f, 6.271f, 2.625f)
                    quadToRelative(2.562f, 2.625f, 2.562f, 6.334f)
                    quadToRelative(0f, 3.75f, -2.562f, 6.416f)
                    quadTo(34.625f, 29f, 30.958f, 29f)
                    quadToRelative(-1.5f, 0f, -2.896f, -0.479f)
                    quadToRelative(-1.395f, -0.479f, -2.479f, -1.521f)
                    lineToRelative(-3.541f, -3.417f)
                    lineToRelative(1.875f, -1.791f)
                    lineToRelative(3.375f, 3.25f)
                    quadToRelative(0.708f, 0.666f, 1.666f, 1f)
                    quadToRelative(0.959f, 0.333f, 2f, 0.333f)
                    quadToRelative(2.584f, 0f, 4.375f, -1.917f)
                    quadToRelative(1.792f, -1.916f, 1.792f, -4.541f)
                    quadToRelative(0f, -2.584f, -1.813f, -4.438f)
                    quadToRelative(-1.812f, -1.854f, -4.354f, -1.854f)
                    quadToRelative(-1f, 0f, -1.958f, 0.333f)
                    quadToRelative(-0.958f, 0.334f, -1.667f, 1.042f)
                    lineTo(14.375f, 27.042f)
                    quadToRelative(-1.083f, 1f, -2.479f, 1.479f)
                    quadTo(10.5f, 29f, 9.042f, 29f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberHomePin(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "home_pin",
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
                    moveTo(15.375f, 21.292f)
                    verticalLineTo(13.5f)
                    lineTo(20f, 10.417f)
                    lineToRelative(4.667f, 3.083f)
                    verticalLineToRelative(7.792f)
                    horizontalLineToRelative(-2.625f)
                    verticalLineToRelative(-5f)
                    horizontalLineTo(18f)
                    verticalLineToRelative(5f)
                    close()
                    moveTo(20f, 33.083f)
                    quadToRelative(5.417f, -4.916f, 8.021f, -8.958f)
                    quadToRelative(2.604f, -4.042f, 2.604f, -7.125f)
                    quadToRelative(0f, -4.833f, -3.063f, -7.896f)
                    quadTo(24.5f, 6.042f, 20f, 6.042f)
                    reflectiveQuadToRelative(-7.562f, 3.062f)
                    quadTo(9.375f, 12.167f, 9.375f, 17f)
                    quadToRelative(0f, 3.083f, 2.646f, 7.125f)
                    reflectiveQuadTo(20f, 33.083f)
                    close()
                    moveToRelative(0f, 3.5f)
                    quadToRelative(-6.667f, -5.708f, -9.958f, -10.562f)
                    quadTo(6.75f, 21.167f, 6.75f, 17f)
                    quadToRelative(0f, -6.208f, 4f, -9.917f)
                    quadToRelative(4f, -3.708f, 9.25f, -3.708f)
                    reflectiveQuadToRelative(9.271f, 3.708f)
                    quadToRelative(4.021f, 3.709f, 4.021f, 9.917f)
                    quadToRelative(0f, 4.167f, -3.292f, 9.021f)
                    quadToRelative(-3.292f, 4.854f, -10f, 10.562f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberChecklist(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "checklist",
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
                    moveTo(23.042f, 14.625f)
                    quadToRelative(-0.542f, 0f, -0.938f, -0.375f)
                    quadToRelative(-0.396f, -0.375f, -0.396f, -0.917f)
                    quadToRelative(0f, -0.541f, 0.396f, -0.937f)
                    reflectiveQuadToRelative(0.938f, -0.396f)
                    horizontalLineToRelative(12.25f)
                    quadToRelative(0.541f, 0f, 0.916f, 0.396f)
                    reflectiveQuadToRelative(0.375f, 0.937f)
                    quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.916f, 0.375f)
                    close()
                    moveToRelative(0f, 13.333f)
                    quadToRelative(-0.542f, 0f, -0.938f, -0.375f)
                    quadToRelative(-0.396f, -0.375f, -0.396f, -0.916f)
                    quadToRelative(0f, -0.542f, 0.396f, -0.938f)
                    quadToRelative(0.396f, -0.396f, 0.938f, -0.396f)
                    horizontalLineToRelative(12.25f)
                    quadToRelative(0.541f, 0f, 0.916f, 0.396f)
                    reflectiveQuadToRelative(0.375f, 0.938f)
                    quadToRelative(0f, 0.541f, -0.375f, 0.916f)
                    reflectiveQuadToRelative(-0.916f, 0.375f)
                    close()
                    moveTo(8.333f, 16.917f)
                    lineToRelative(-4f, -3.959f)
                    quadToRelative(-0.375f, -0.416f, -0.375f, -0.937f)
                    quadToRelative(0f, -0.521f, 0.375f, -0.938f)
                    quadToRelative(0.417f, -0.375f, 0.938f, -0.375f)
                    quadToRelative(0.521f, 0f, 0.937f, 0.375f)
                    lineToRelative(3.042f, 3.042f)
                    lineToRelative(6.375f, -6.417f)
                    quadToRelative(0.417f, -0.416f, 0.937f, -0.395f)
                    quadToRelative(0.521f, 0.02f, 0.938f, 0.395f)
                    quadToRelative(0.375f, 0.417f, 0.375f, 0.959f)
                    quadToRelative(0f, 0.541f, -0.375f, 0.916f)
                    lineToRelative(-7.333f, 7.334f)
                    quadToRelative(-0.417f, 0.375f, -0.938f, 0.375f)
                    quadToRelative(-0.521f, 0f, -0.896f, -0.375f)
                    close()
                    moveToRelative(0f, 13.333f)
                    lineToRelative(-4f, -3.958f)
                    quadToRelative(-0.375f, -0.417f, -0.375f, -0.938f)
                    quadToRelative(0f, -0.521f, 0.375f, -0.937f)
                    quadToRelative(0.417f, -0.375f, 0.938f, -0.375f)
                    quadToRelative(0.521f, 0f, 0.937f, 0.375f)
                    lineToRelative(3.042f, 3.041f)
                    lineToRelative(6.375f, -6.416f)
                    quadToRelative(0.417f, -0.417f, 0.937f, -0.396f)
                    quadToRelative(0.521f, 0.021f, 0.938f, 0.396f)
                    quadToRelative(0.375f, 0.416f, 0.375f, 0.958f)
                    reflectiveQuadToRelative(-0.375f, 0.917f)
                    lineToRelative(-7.333f, 7.333f)
                    quadToRelative(-0.417f, 0.375f, -0.938f, 0.375f)
                    quadToRelative(-0.521f, 0f, -0.896f, -0.375f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun rememberSort(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "sort",
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
                    moveTo(13.708f, 29.333f)
                    horizontalLineTo(6.542f)
                    quadToRelative(-0.542f, 0f, -0.938f, -0.395f)
                    quadToRelative(-0.396f, -0.396f, -0.396f, -0.938f)
                    quadToRelative(0f, -0.542f, 0.396f, -0.938f)
                    quadToRelative(0.396f, -0.395f, 0.938f, -0.395f)
                    horizontalLineToRelative(7.166f)
                    quadToRelative(0.542f, 0f, 0.938f, 0.395f)
                    quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                    quadToRelative(0f, 0.583f, -0.396f, 0.958f)
                    reflectiveQuadToRelative(-0.938f, 0.375f)
                    close()
                    moveTo(33.5f, 12.792f)
                    horizontalLineTo(6.542f)
                    quadToRelative(-0.542f, 0f, -0.938f, -0.375f)
                    quadToRelative(-0.396f, -0.375f, -0.396f, -0.959f)
                    quadToRelative(0f, -0.541f, 0.396f, -0.937f)
                    reflectiveQuadToRelative(0.938f, -0.396f)
                    horizontalLineTo(33.5f)
                    quadToRelative(0.542f, 0f, 0.917f, 0.396f)
                    reflectiveQuadToRelative(0.375f, 0.937f)
                    quadToRelative(0f, 0.584f, -0.375f, 0.959f)
                    reflectiveQuadToRelative(-0.917f, 0.375f)
                    close()
                    moveToRelative(-9.875f, 8.25f)
                    horizontalLineTo(6.542f)
                    quadToRelative(-0.542f, 0f, -0.938f, -0.375f)
                    quadToRelative(-0.396f, -0.375f, -0.396f, -0.959f)
                    quadToRelative(0f, -0.541f, 0.396f, -0.916f)
                    reflectiveQuadToRelative(0.938f, -0.375f)
                    horizontalLineToRelative(17.083f)
                    quadToRelative(0.542f, 0f, 0.917f, 0.395f)
                    quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                    quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.917f, 0.375f)
                    close()
                }
            }.build()
        }
    }
}
