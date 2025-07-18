package io.github.henriquehorbovyi.particles.buttons.simple

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
class SimpleButtonColors(
    val background: Color,
    val disabledBackground: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
) {
    @Stable
    internal fun containerColor(enabled: Boolean): Color = if (enabled) background else disabledBackground

    @Stable
    internal fun contentColor(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor
}

object SimpleButtonDefaults {
    private val background: Color = Color.Black
    private val disabledBackground: Color = Color.DarkGray
    private val contentColor: Color = Color.White
    private val disabledContentColor: Color = Color.LightGray

    val MinWidth = 42.dp
    val MinHeight = 42.dp
    val elevation = 2.dp

    private val ButtonHorizontalPadding = 24.dp
    private val ButtonVerticalPadding = 8.dp

    val ContentPadding =
        PaddingValues(
            start = ButtonHorizontalPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding,
        )

    val shape: RoundedCornerShape =
        RoundedCornerShape(4.dp)

    fun colors(
        background: Color = this.background,
        disabledBackground: Color = this.disabledBackground,
        contentColor: Color = this.contentColor,
        disabledContentColor: Color = this.disabledContentColor,
    ): SimpleButtonColors =
        SimpleButtonColors(
            background = background,
            disabledBackground = disabledBackground,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
        )
}
