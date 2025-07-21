package io.github.henriquehorbovyi.particles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import kotlin.math.max

/**
 * Represents an item in the timeline.
 *
 * @property title A composable function to render the title of the timeline item.
 * @property description An optional composable function to render the description of the timeline item.
 * @property circleColor The color of the circle for this item. Defaults to `Color.Gray`.
 * @property lineColor The color of the lines connecting this item. Defaults to `Color.Gray`.
 * @property lineStyle The style of the lines (e.g., stroke width). Defaults to `Stroke(width = 2.dp.toPx())`.
 *                     You can create more complex styles using `Stroke`.
 */
data class TimelineItem(
    val title: @Composable () -> Unit,
    val description: (@Composable () -> Unit)? = null,
    val circleColor: Color = Color.Gray,
    val lineColor: Color = Color.Gray,
    val lineStyle: Stroke = Stroke(width = 2f) // Default stroke width 2f
)

/**
 * A composable that displays a list of [TimelineItem]s in a vertical timeline format.
 *
 * @param items The list of [TimelineItem]s to display.
 * @param modifier Optional [Modifier] for this component.
 * @param itemSpacing The vertical spacing between timeline items. Defaults to `16.dp`.
 * @param circleRadius The radius of the circle in each timeline item. Defaults to `6.dp`.
 * @param lineWidth The width of the connecting lines. Defaults to `2.dp`.
 * @param contentPadding The padding around the content (title and description) of each item. Defaults to `8.dp`.
 * @param circleCenterYFromTop The vertical distance from the top of each item to the center of its circle.
 *                             This helps in aligning the circle with your title composable. Defaults to `16.dp`.
 */
@Composable
fun Timeline(
    items: List<TimelineItem>,
    modifier: Modifier = Modifier,
    circleRadius: Dp = 6.dp,
    lineWidth: Dp = 2.dp,
    contentPadding: Dp = 8.dp,
    circleCenterYFromTop: Dp = 16.dp // Default position for circle center
) {
    Column(
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            TimelineItemComponent(
                item = item,
                isFirstItem = index == 0,
                isLastItem = index == items.lastIndex,
                circleRadius = circleRadius,
                lineWidth = lineWidth,
                contentPadding = contentPadding,
                circleCenterYFromTop = circleCenterYFromTop
            )
        }
    }
}

@Composable
internal fun TimelineItemComponent(
    item: TimelineItem,
    isFirstItem: Boolean,
    isLastItem: Boolean,
    circleRadius: Dp,
    lineWidth: Dp,
    contentPadding: Dp,
    circleCenterYFromTop: Dp
) {
    val density = LocalDensity.current
    val circleRadiusPx = with(density) { circleRadius.toPx() }
    val lineWidthPx = with(density) { lineWidth.toPx() }
    val actualCircleCenterYPx = with(density) { circleCenterYFromTop.toPx() }

    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .width(max(lineWidth, circleRadius * 2)) // Ensure canvas is wide enough for circle or line
        ) {
            val canvasCenterX = size.width / 2f

            // Draw top line if not the first item
            if (!isFirstItem) {
                drawLine(
                    color = item.lineColor,
                    start = Offset(canvasCenterX, 0f),
                    end = Offset(canvasCenterX, actualCircleCenterYPx - circleRadiusPx),
                    strokeWidth = lineWidthPx,
                )
            }

            // Draw bottom line if not the last item
            if (!isLastItem) {
                drawLine(
                    color = item.lineColor,
                    start = Offset(canvasCenterX, actualCircleCenterYPx + circleRadiusPx),
                    end = Offset(canvasCenterX, size.height),
                    strokeWidth = lineWidthPx,
                )
            }

            // Draw circle
            drawCircle(
                color = item.circleColor,
                radius = circleRadiusPx,
                center = Offset(canvasCenterX, actualCircleCenterYPx)
            )
        }

        Spacer(modifier = Modifier.width(contentPadding))

        Column(modifier = Modifier.padding(vertical = contentPadding / 2)) { // Add some vertical padding to content
            item.title()
            item.description?.invoke()
        }
    }
}
