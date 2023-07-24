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

object CommentIcons {
    @Composable
    fun rememberPerson_2(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "person_2",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
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
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(15.625f, 19.917f)
                    quadToRelative(-1.208f, 0f, -2.021f, -0.896f)
                    quadToRelative(-0.812f, -0.896f, -0.646f, -2.063f)
                    lineToRelative(0.667f, -4.791f)
                    quadToRelative(0.292f, -2.375f, 2.125f, -3.917f)
                    quadTo(17.583f, 6.708f, 20f, 6.708f)
                    reflectiveQuadToRelative(4.229f, 1.542f)
                    quadToRelative(1.813f, 1.542f, 2.188f, 3.917f)
                    lineToRelative(0.625f, 4.791f)
                    quadToRelative(0.166f, 1.167f, -0.646f, 2.063f)
                    quadToRelative(-0.813f, 0.896f, -1.979f, 0.896f)
                    close()
                    moveToRelative(-0.042f, -2.625f)
                    horizontalLineToRelative(8.834f)
                    lineToRelative(-0.625f, -4.75f)
                    quadToRelative(-0.209f, -1.375f, -1.292f, -2.271f)
                    quadToRelative(-1.083f, -0.896f, -2.5f, -0.896f)
                    reflectiveQuadToRelative(-2.5f, 0.896f)
                    quadToRelative(-1.083f, 0.896f, -1.292f, 2.271f)
                    close()
                    moveTo(9.542f, 32.958f)
                    quadToRelative(-1.084f, 0f, -1.855f, -0.77f)
                    quadToRelative(-0.77f, -0.771f, -0.77f, -1.855f)
                    verticalLineToRelative(-1.375f)
                    quadToRelative(0f, -1.5f, 0.75f, -2.604f)
                    reflectiveQuadToRelative(1.958f, -1.687f)
                    quadToRelative(2.708f, -1.25f, 5.25f, -1.875f)
                    reflectiveQuadTo(20f, 22.167f)
                    quadToRelative(2.583f, 0f, 5.104f, 0.645f)
                    quadToRelative(2.521f, 0.646f, 5.229f, 1.855f)
                    quadToRelative(1.25f, 0.583f, 2f, 1.687f)
                    reflectiveQuadToRelative(0.75f, 2.604f)
                    verticalLineToRelative(1.375f)
                    quadToRelative(0f, 1.084f, -0.771f, 1.855f)
                    quadToRelative(-0.77f, 0.77f, -1.854f, 0.77f)
                    close()
                    moveToRelative(0f, -2.625f)
                    horizontalLineToRelative(20.916f)
                    verticalLineToRelative(-1.375f)
                    quadToRelative(0f, -0.583f, -0.354f, -1.125f)
                    quadToRelative(-0.354f, -0.541f, -0.854f, -0.791f)
                    quadToRelative(-2.542f, -1.209f, -4.729f, -1.709f)
                    quadToRelative(-2.188f, -0.5f, -4.521f, -0.5f)
                    quadToRelative(-2.333f, 0f, -4.542f, 0.5f)
                    quadToRelative(-2.208f, 0.5f, -4.708f, 1.709f)
                    quadToRelative(-0.542f, 0.25f, -0.875f, 0.791f)
                    quadToRelative(-0.333f, 0.542f, -0.333f, 1.125f)
                    close()
                    moveToRelative(10.458f, 0f)
                    close()
                    moveToRelative(0f, -13.041f)
                    close()
                }
            }.build()
        }
    }
}
