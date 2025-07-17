package io.henriquehorbovyi.particles.buttons.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.henriquehorbovyi.particles.buttons.simple.SimpleButton
import io.henriquehorbovyi.particles.buttons.simple.SimpleButtonDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A button that displays a loading indicator and custom content.
 * This overload allows for providing a custom composable for the loading indicator.
 *
 * @param content The content to display inside the button.
 * @param loadingContent The content to display as the loading indicator.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param isLoading Whether the button is currently in a loading state.
 * @param modifier The modifier to be applied to the button.
 * @param isContentVisible Whether the [content] should be visible or not.
 * @param progressIndicatorSide Determines on which side of the [content] the loading indicator appears.
 * @param horizontalArrangementSpace The horizontal space between the loading indicator and the content.
 * @param contentPadding The padding around the button's content.
 */
@Composable
fun LoadingButton(
    content: @Composable RowScope.() -> Unit,
    loadingContent: (@Composable RowScope.() -> Unit),
    onClick: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    progressIndicatorSide: ProgressIndicatorSide = ProgressIndicatorSide.Start,
    horizontalArrangementSpace: Dp = 8.dp,
    contentPadding: PaddingValues = SimpleButtonDefaults.ContentPadding,
) {
    SimpleButton(
        modifier = modifier,
        enabled = !isLoading,
        onClick = onClick,
        contentPadding = contentPadding,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(horizontalArrangementSpace),
            ) {
                if (progressIndicatorSide == ProgressIndicatorSide.Start) {
                    if (isLoading) {
                        loadingContent()
                    }
                    if (isContentVisible) {
                        content()
                    }
                } else {
                    if (isContentVisible) {
                        content()
                    }
                    if (isLoading) {
                        loadingContent()
                    }
                }
            }
        }
    )
}

/**
 * A button that displays a default circular progress indicator and custom content.
 * This overload uses a default [CircularProgressIndicator] and allows its configuration.
 *
 * @param content The content to display inside the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param isLoading Whether the button is currently in a loading state.
 * @param modifier The modifier to be applied to the button.
 * @param progressIndicatorConfig Configuration for the default circular progress indicator.
 * @param isContentVisible Whether the [content] should be visible or not.
 * @param progressIndicatorSide Determines on which side of the [content] the loading indicator appears.
 * @param horizontalArrangementSpace The horizontal space between the loading indicator and the content.
 * @param contentPadding The padding around the button's content.
 */
@Composable
fun LoadingButton(
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    progressIndicatorConfig: ProgressIndicatorConfig = ProgressIndicatorConfig(),
    isContentVisible: Boolean = true,
    progressIndicatorSide: ProgressIndicatorSide = ProgressIndicatorSide.Start,
    horizontalArrangementSpace: Dp = 8.dp,
    contentPadding: PaddingValues = SimpleButtonDefaults.ContentPadding,
) {
    SimpleButton(
        modifier = modifier,
        enabled = !isLoading,
        onClick = onClick,
        contentPadding = contentPadding,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(horizontalArrangementSpace),
            ) {
                if (progressIndicatorSide == ProgressIndicatorSide.Start) {
                    if (isLoading) {
                        DefaultCircularProgressIndicator(
                            progressIndicatorConfig = progressIndicatorConfig
                        )
                    }
                    if (isContentVisible) {
                        content()
                    }
                } else {
                    if (isContentVisible) {
                        content()
                    }
                    if (isLoading) {
                        DefaultCircularProgressIndicator(
                            progressIndicatorConfig = progressIndicatorConfig
                        )
                    }
                }
            }
        }
    )
}

@Composable
internal fun DefaultCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progressIndicatorConfig: ProgressIndicatorConfig = ProgressIndicatorConfig()
) {
    CircularProgressIndicator(
        modifier = modifier.size(16.dp),
        color = progressIndicatorConfig.color,
        trackColor = progressIndicatorConfig.trackColor,
        strokeWidth = progressIndicatorConfig.strokeWidth,
        strokeCap = progressIndicatorConfig.strokeCap
    )
}

data class ProgressIndicatorConfig(
    val color: Color = Color.White,
    val trackColor: Color = Color.Gray,
    val strokeWidth: Dp = 2.dp,
    val strokeCap: StrokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
)

enum class ProgressIndicatorSide {
    Start, End
}


@Preview()
@Composable
private fun LoadingButtonPreview() {
    LoadingButton(
        content = { Text("Submit") },
        isLoading = true,
        onClick = {}
    )
}
