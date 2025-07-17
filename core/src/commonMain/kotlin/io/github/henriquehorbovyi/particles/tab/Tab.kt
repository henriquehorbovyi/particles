package io.henriquehorbovyi.particles.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

// TODO: decide whether to keep this one or not. Should I use a Material3 PrimaryTabRow instead?
@Composable
fun TabContainer(
    tabs: List<String>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onTabChanged: (index: Int) -> Unit = {},
    colors: TabContainerColors = TabDefaultsDefaults.colors(),
    tabHeight: Dp = 42.dp,
    tabSpacing: Dp = 4.dp
) {
    var internalSelectedIndex by remember { mutableStateOf(selectedIndex) }

    val density = LocalDensity.current

    val tabCount = tabs.size
    val tabWidthsPx = remember { mutableStateListOf<Int>() }
    while (tabWidthsPx.size < tabCount) tabWidthsPx.add(0)
    while (tabWidthsPx.size > tabCount) tabWidthsPx.removeLast()

    val indicatorOffsetPx by remember {
        derivedStateOf {
            tabWidthsPx
                .take(internalSelectedIndex)
                .sum()
                .plus(internalSelectedIndex * with(density) { tabSpacing.roundToPx() })
        }
    }
    val indicatorWidthPx by remember {
        derivedStateOf { tabWidthsPx.getOrNull(internalSelectedIndex) ?: 0 }
    }

    val indicatorOffsetDp by animateDpAsState(targetValue = with(density) { indicatorOffsetPx.toDp() })
    val indicatorWidthDp by animateDpAsState(targetValue = with(density) { indicatorWidthPx.toDp() })

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colors.containerBackground)
            .padding(4.dp)
    ) {
        TabIndicator(
            indicatorOffset = indicatorOffsetDp,
            tabWidth = indicatorWidthDp,
            tabHeight = tabHeight,
            color = colors.selectedTabBackground
        )

        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(tabSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, value ->
                val onTabSizeChanged = rememberUpdatedState { size: IntSize ->
                    if (tabWidthsPx[index] != size.width) {
                        tabWidthsPx[index] = size.width
                    }
                }

                Tab(
                    text = value,
                    isSelected = index == internalSelectedIndex,
                    index = index,
                    selectedBackground = Color.Transparent,
                    unselectedBackground = Color.Transparent,
                    selectedTextColor = colors.selectedTabColor,
                    unselectedTextColor = colors.unselectedTabColor,
                    onSelect = {
                        internalSelectedIndex = it
                        onTabChanged(internalSelectedIndex)
                    },
                    modifier = Modifier
                        .defaultMinSize(minHeight = tabHeight)
                        .onSizeChanged { onTabSizeChanged.value(it) }
                )
            }
        }
    }
}

@Composable
private fun Tab(
    text: String,
    index: Int,
    modifier: Modifier = Modifier,
    selectedBackground: Color,
    unselectedBackground: Color,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    isSelected: Boolean = false,
    onSelect: (index: Int) -> Unit = {},
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) selectedTextColor else unselectedTextColor
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) selectedBackground else unselectedBackground)
            .clickable { onSelect(index) }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor)
    }
}

@Preview
@Composable
private fun ThreeStateSwitchPreview() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabContainer(tabs = listOf("Code", "Issues", "Docs"))
    }
}
