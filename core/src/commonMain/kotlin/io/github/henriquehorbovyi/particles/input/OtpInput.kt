package io.github.henriquehorbovyi.particles.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPInput(
    otpLength: Int = 6,
    onOtpComplete: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(6.dp),
) {
    var otpValue by remember { mutableStateOf("") }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    var imeAction by remember { mutableStateOf(ImeAction.Next) }

    LaunchedEffect(Unit) {
        focusRequesters.firstOrNull()?.requestFocus()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        repeat(otpLength) { index ->
            val currentChar = otpValue.getOrNull(index)?.toString() ?: ""
            Box {
                TextField(
                    value = currentChar,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            val candidateOtp = otpValue.take(index) + newValue + otpValue.drop(index + 1)
                            otpValue = candidateOtp.take(otpLength)

                            if (newValue.isNotEmpty()) {
                                if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
                                if (otpValue.length == otpLength) {
                                    onOtpComplete(otpValue)
                                    imeAction = ImeAction.Done
                                }
                            } else if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                                imeAction = ImeAction.Next
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = imeAction
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = shape,
                    modifier = Modifier
                        .sizeIn(
                            minHeight = 42.dp, minWidth = 42.dp,
                            maxHeight = 56.dp, maxWidth = 56.dp
                        )
                        .focusRequester(focusRequesters[index])
                )
            }
        }
    }
}
