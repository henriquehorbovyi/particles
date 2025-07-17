package io.henriquehorbovyi.particles.tab

import androidx.compose.ui.graphics.Color

data class TabContainerColors(
    val containerBackground: Color,
    val selectedTabBackground: Color,
    val unselectedTabBackground: Color,
    val selectedTabColor: Color,
    val unselectedTabColor: Color
)

object TabDefaultsDefaults {
    private val background: Color = Color(0xff202020)
    private val selectedBackground: Color = Color.Black
    private val unselectedBackground: Color = Color(0xff202020)
    private val selectedTextColor: Color = Color.White
    private val unselectedTextColor: Color = Color(0xffa0a0a0)

    fun colors(): TabContainerColors = TabContainerColors(
        containerBackground = background,
        selectedTabBackground = selectedBackground,
        unselectedTabBackground = unselectedBackground,
        selectedTabColor = selectedTextColor,
        unselectedTabColor = unselectedTextColor
    )
}
