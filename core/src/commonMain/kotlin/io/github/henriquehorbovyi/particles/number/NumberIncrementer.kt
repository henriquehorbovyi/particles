package io.github.henriquehorbovyi.particles.number

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.henriquehorbovyi.particles.buttons.simple.SimpleButton
import io.github.henriquehorbovyi.particles.buttons.simple.SimpleButtonDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A composable that allows users to increment and decrement a numerical value.
 * It provides buttons for increasing and decreasing the value, and displays the current value.
 * The appearance and behavior can be customized through various parameters.
 *
 * @param value The current integer value to be displayed and manipulated.
 * @param onValueChange Callback that is invoked when the value changes, providing the new value.
 * @param modifier The modifier to be applied to the NumberIncrementer.
 * @param contentPadding The padding applied to the content within the NumberIncrementer.
 * @param shape The shape of the NumberIncrementer's background and border.
 * @param increaseButton An optional composable lambda to customize the increase button.
 * If null, a default button with a "+" icon is used.
 * @param decreaseButton An optional composable lambda to customize the decrease button.
 * If null, a default button with a "-" icon is used.
 * @param content An optional composable lambda to customize the display of the value.
 * If null, a default [Text] composable is used to display the [value].
 * @param increaseInteractionSource The [MutableInteractionSource] for the increase button,
 * allowing observation of its interactions.
 * @param decreaseInteractionSource The [MutableInteractionSource] for the decrease button,
 * allowing observation of its interactions.
 * @param colors The [NumberIncrementerColors] to customize the colors of the NumberIncrementer.
 * @param border The [BorderStroke] to be applied to the NumberIncrementer.
 * @param initialDelay The initial delay in milliseconds before the repeat action starts when a button is pressed and held.
 * @param repeatDelay The delay in milliseconds between subsequent value changes when a button is pressed and held.
 */
@Composable
fun NumberIncrementer(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = NumberIncrementerDefaults.contentPadding,
    shape: Shape = NumberIncrementerDefaults.shape,
    increaseButton: (@Composable RowScope.() -> Unit)? = null,
    decreaseButton: (@Composable RowScope.() -> Unit)? = null,
    content: (@Composable RowScope.() -> Unit)? = null,
    increaseInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    decreaseInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: NumberIncrementerColors = NumberIncrementerDefaults.colors(),
    border: BorderStroke = NumberIncrementerDefaults.border,
    initialDelay: Long = NumberIncrementerDefaults.INITIAL_DELAY,
    repeatDelay: Long = NumberIncrementerDefaults.REPEAT_DELAY,
) {
    var internalValue by remember { mutableStateOf(value) }

    increaseInteractionSource.onIsPressedStateChanged(
        initialDelay = initialDelay,
        repeatDelay = repeatDelay,
        onPressBegin = { onValueChange(++internalValue) },
    )

    decreaseInteractionSource.onIsPressedStateChanged(
        initialDelay = initialDelay,
        repeatDelay = repeatDelay,
        onPressBegin = { onValueChange(--internalValue) },
    )

    val mergedStyle =
        LocalTextStyle.current.merge(MaterialTheme.typography.labelLarge)

    CompositionLocalProvider(
        LocalContentColor provides colors.content,
        LocalTextStyle provides mergedStyle,
    ) {
        Row(
            modifier =
                modifier
                    .clip(shape)
                    .background(colors.background)
                    .border(border, shape)
                    .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            decreaseButton?.invoke(this) ?: SimpleButton(
                colors =
                    SimpleButtonDefaults.colors(
                        background = colors.buttonBackground,
                        contentColor = colors.buttonContent,
                    ),
                content = { Text("-", fontSize = 18.sp) },
                contentPadding = PaddingValues(0.dp),
                interactionSource = decreaseInteractionSource,
            )

            content?.invoke(this) ?: Text(
                text = value.toString(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            increaseButton?.invoke(this) ?: SimpleButton(
                colors =
                    SimpleButtonDefaults.colors(
                        background = colors.buttonBackground,
                        contentColor = colors.buttonContent,
                    ),
                content = { Text("+", fontSize = 18.sp) },
                contentPadding = PaddingValues(0.dp),
                interactionSource = increaseInteractionSource,
            )
        }
    }
}

@Preview
@Composable
private fun NumberIncrementerPreview() {
    var value by remember { mutableStateOf(0) }

    NumberIncrementer(
        value = value,
        onValueChange = { value = it },
    )
}
