package io.github.henriquehorbovyi.particles.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
    indicatorOffset: Dp,
    tabWidth: Dp,
    tabHeight: Dp,
    color: Color,
) {
    Box(
        modifier =
            modifier
                .offset(x = indicatorOffset)
                .defaultMinSize(minWidth = tabWidth, minHeight = tabHeight)
                .clip(RoundedCornerShape(8.dp))
                .background(color)
                .padding(16.dp),
    )
}
