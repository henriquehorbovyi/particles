package io.github.henriquehorbovyi.particles.number

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import kotlinx.coroutines.delay

@Suppress("ComposableNaming")
@Composable
fun InteractionSource.onIsPressedStateChanged(
    onPressBegin: () -> Unit,
    onPressEnd: () -> Unit = {},
    initialDelay: Long = 600L,
    repeatDelay: Long = 100L,
) {
    val isPressed by this.collectIsPressedAsState()
    LaunchedEffect(isPressed) {
        if (isPressed) {
            onPressBegin()
            delay(initialDelay)
            while (isPressed) {
                onPressBegin()
                delay(repeatDelay)
            }
            // onPressEnd will be called by the else branch below
        } else {
            onPressEnd()
        }
    }
}
