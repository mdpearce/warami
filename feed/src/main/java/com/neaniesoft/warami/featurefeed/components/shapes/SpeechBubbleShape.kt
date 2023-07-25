package com.neaniesoft.warami.featurefeed.components.shapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class SpeechBubbleShape(
    private val roundedCorner: Dp = 8.dp,
    private val triangleSize: Dp = 16.dp,
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val cornerRadius = roundedCorner.value * density.density
        val triangleSideLength = triangleSize.value * density.density

        return Outline.Generic(
            Path().apply {
                // Start at top-left at the base of the triangle.
                moveTo(0f, triangleSideLength)
                // Draw line to apex of triangle.
                lineTo(0f, 0f)
                // Draw line to end of triangle.
                lineTo(triangleSideLength, triangleSideLength)
                // Draw line to top-right with rounded corner.
                arcTo(
                    Rect(
                        left = size.width - 2 * cornerRadius,
                        top = triangleSideLength,
                        right = size.width,
                        bottom = 2 * cornerRadius + triangleSideLength,
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false,
                )
                // Draw line to bottom-right with rounded corner.
                lineTo(size.width, size.height - cornerRadius)
                arcTo(
                    Rect(
                        left = size.width - 2 * cornerRadius,
                        top = size.height - 2 * cornerRadius,
                        right = size.width,
                        bottom = size.height,
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false,
                )
                // Draw line to bottom-left with rounded corner.
                lineTo(cornerRadius, size.height)
                arcTo(
                    Rect(
                        left = 0f,
                        top = size.height - 2 * cornerRadius,
                        right = 2 * cornerRadius,
                        bottom = size.height,
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false,
                )
                // Draw line back to the base of the triangle.
                lineTo(0f, cornerRadius + triangleSideLength)
                close()
            },
        )
    }
}
