package io.github.henriquehorbovyi.particles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.henriquehorbovyi.core.generated.resources.Res
import io.github.henriquehorbovyi.core.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

// TODO: Create a RatingStarDefaults class to keep the default values (colors, sizes, shapes).
//  allow lambdas to make customize icons (icon)

@Composable
fun RatingStars(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    maxRating: Int = 5,
    allowHalfStars: Boolean = true,
    iconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    filledStarColor: Color = Color(0xFFFFD700),
    halfStarEmptyPortionColor: Color = Color.LightGray,
    fullEmptyStarColor: Color = Color.LightGray
) {
    val starPainter = painterResource(Res.drawable.ic_star)
    val iconButtonSize = iconSize - (iconSize.value * .25f).dp

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        repeat(maxRating) { index ->
            val starValue = index + 1
            val isFilled = rating >= starValue
            val isHalfFilled = allowHalfStars && rating >= (starValue - 0.5f) && rating < starValue

            IconButton(
                onClick = {
                    val newRatingValue = if (allowHalfStars) {
                        val clickedStarFullValue = starValue.toFloat()
                        val clickedStarHalfValue = starValue - 0.5f
                        val clickedStarPreviousValue = (starValue - 1).toFloat()

                        // Clicked star is currently half-filled -> make it full
                        when (rating) {
                            clickedStarHalfValue -> clickedStarFullValue
                            // Clicked star is currently full -> make it empty (previous star's value)
                            clickedStarFullValue -> clickedStarPreviousValue
                            else -> {
                                // Clicked star is visually empty or a higher star is rated
                                if (rating > clickedStarFullValue) {
                                    // Higher rating exists (e.g. rating 3.5, clicked star 2) -> make clicked star full
                                    clickedStarFullValue
                                } else {
                                    // Clicked star is visually empty or not fully covered -> make it half
                                    clickedStarHalfValue
                                }
                            }
                        }
                    } else {
                        if (rating >= starValue) {
                            (starValue - 1).toFloat()
                        } else {
                            starValue.toFloat()
                        }
                    }
                    onRatingChange(newRatingValue.coerceIn(0f, maxRating.toFloat()))
                },
                modifier = Modifier.size(iconButtonSize)
            ) {
                if (isHalfFilled) {
                    HalfPaintedIcon(
                        painter = starPainter,
                        filledColor = filledStarColor,
                        emptyColor = halfStarEmptyPortionColor,
                        modifier = Modifier.size(iconSize),
                    )
                } else {
                    Image(
                        painter = starPainter,
                        contentDescription = "Star $starValue",
                        colorFilter = ColorFilter.tint(
                            if (isFilled) filledStarColor else fullEmptyStarColor,
                        ),
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
}

@Composable
fun HalfPaintedIcon(
    painter: Painter,
    filledColor: Color,
    emptyColor: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        // Draw the full star with the empty color first as a background
        with(painter) {
            draw(size, colorFilter = ColorFilter.tint(emptyColor))
        }
        // Draw the filled half on top, clipped to the left half
        clipRect(right = size.width / 2) {
            with(painter) {
                draw(size, colorFilter = ColorFilter.tint(filledColor))
            }
        }
    }
}
