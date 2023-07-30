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
}
