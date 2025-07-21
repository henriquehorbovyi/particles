package io.github.henriquehorbovyi.particles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// TODO: Refactor it to be more flexible and customizable
@Composable
fun FacePile(
    items: List<String>,
    maxVisible: Int = 3,
    avatarSize: Dp = 42.dp,
    offset: Dp = (-12).dp,
    modifier: Modifier = Modifier
) {
    val visibleItems = items.take(maxVisible)
    val overflowCount = items.size - maxVisible

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(offset)
    ) {
        visibleItems.forEach { item ->
            Avatar(
                text = item.take(2).uppercase(),
                size = avatarSize,
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }

        if (overflowCount > 0) {
            Avatar(
                text = "+$overflowCount",
                size = avatarSize,
                backgroundColor = Color.Gray
            )
        }
    }
}

@Composable
internal fun Avatar(
    text: String,
    size: Dp,
    backgroundColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(backgroundColor, CircleShape)
            .border(1.dp, Color.White, CircleShape)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
