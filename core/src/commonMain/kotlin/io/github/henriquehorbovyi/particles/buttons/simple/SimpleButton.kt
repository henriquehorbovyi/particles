package io.github.henriquehorbovyi.particles.buttons.simple

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A simple, customizable button composable.
 *
 * @param content The content to display inside the button.
 * @param modifier The modifier to be applied to the button.
 * @param colors The [SimpleButtonColors] to be used for this button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable.
 * @param shape Defines the shape of the button's container.
 * @param interactionSource The [MutableInteractionSource] representing the stream of
 * interactions for this button. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe interactions and customize the
 * appearance / behavior of this button in different states.
 * @param contentPadding The padding values to be applied to the content of the button.
 * @param border The border to draw around the button.
 * @param elevation The elevation for the button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleButton(
    content: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: SimpleButtonColors = SimpleButtonDefaults.colors(),
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: RoundedCornerShape = SimpleButtonDefaults.shape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = SimpleButtonDefaults.ContentPadding,
    border: BorderStroke? = null,
    elevation: Dp = SimpleButtonDefaults.elevation,
) {
    val containerColor = colors.containerColor(enabled)
    val contentColor = colors.contentColor(enabled)
    val tonalElevationEnabled = LocalTonalElevationEnabled.current

    Box(
        modifier =
            modifier
                .semantics { role = Role.Button }
                .defaultMinSize(
                    minWidth = SimpleButtonDefaults.MinWidth,
                    minHeight = SimpleButtonDefaults.MinHeight,
                ).minimumInteractiveComponentSize()
                .surface(
                    shape = shape,
                    backgroundColor = containerColor,
                    border = border,
                    shadowElevation = with(LocalDensity.current) { elevation.toPx() },
                ).clickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.Button,
                    indication =
                        ripple(
                            bounded = true,
                            radius = Int.MAX_VALUE.dp,
                            color = contentColor,
                        ),
                    interactionSource = interactionSource,
                ),
        propagateMinConstraints = true,
    ) {
        val mergedStyle =
            LocalTextStyle.current.merge(MaterialTheme.typography.labelLarge)

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                Modifier.align(Alignment.Center).padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Float,
) = this
    .then(
        if (shadowElevation > 0f) {
            Modifier.graphicsLayer(
                shadowElevation = shadowElevation,
                shape = shape,
                clip = false,
            )
        } else {
            Modifier
        },
    ).then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)

@Preview
@Composable
private fun SimpleButtonPreview() {
    SimpleButton(
        content = { Text("Hello, world!", style = MaterialTheme.typography.bodyMedium) },
        onClick = { /* Handle click */ },
    )
}
