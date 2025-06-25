package dev.henriquehorbovyi.particles.number

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object NumberIncrementerDefaults {
    val contentPadding: PaddingValues = PaddingValues(4.dp)
    val shape: Shape = RoundedCornerShape(8.dp)
    val border: BorderStroke = BorderStroke(0.dp, Color.Transparent)

    const val INITIAL_DELAY: Long = 600L
    const val REPEAT_DELAY: Long = 100L

    fun colors(
        background: Color = Color(0xFF2a2a2a),
        content: Color = Color.White,
        buttonBackground: Color = Color.Black,
        buttonContent: Color = Color.White,
    ): NumberIncrementerColors {
        return NumberIncrementerColors(
            background = background,
            content = content,
            buttonBackground = buttonBackground,
            buttonContent = buttonContent,
        )
    }
}

class NumberIncrementerColors(
    val background: Color,
    val content: Color,
    val buttonBackground: Color,
    val buttonContent: Color,
)
