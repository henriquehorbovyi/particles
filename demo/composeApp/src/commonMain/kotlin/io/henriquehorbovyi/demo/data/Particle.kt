package io.henriquehorbovyi.demo.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.henriquehorbovyi.particles.FacePile
import io.github.henriquehorbovyi.particles.RatingStars
import io.github.henriquehorbovyi.particles.Stepper
import io.github.henriquehorbovyi.particles.StepperItem
import io.github.henriquehorbovyi.particles.Timeline
import io.github.henriquehorbovyi.particles.TimelineItem
import io.github.henriquehorbovyi.particles.buttons.loading.LoadingButton
import io.github.henriquehorbovyi.particles.buttons.loading.ProgressIndicatorSide
import io.github.henriquehorbovyi.particles.buttons.simple.SimpleButton
import io.github.henriquehorbovyi.particles.cards.ExpandableCard
import io.github.henriquehorbovyi.particles.input.OTPInput
import io.github.henriquehorbovyi.particles.input.SearchBar
import io.github.henriquehorbovyi.particles.number.NumberIncrementer
import io.github.henriquehorbovyi.particles.number.NumberIncrementerDefaults
import io.github.henriquehorbovyi.particles.tab.TabContainer
import kotlinx.coroutines.delay

data class ParticleComponent(
    val name: String,
    val composable: @Composable ColumnScope.() -> Unit,
)

val particles: List<ParticleComponent> =
    listOf(
        ParticleComponent(
            name = "Simple Button",
            composable = {
                SimpleButton(content = { Text("Click me!") })
            },
        ),
        ParticleComponent(
            name = "Loading Button",
            composable = {
                var isLoading by remember { mutableStateOf(false) }
                var isLoading2 by remember { mutableStateOf(false) }

                val text = if (isLoading) "Deploying" else "Deploy"
                val text2 = if (isLoading2) "Uploading" else "Upload"

                LaunchedEffect(isLoading) {
                    if (isLoading) {
                        delay(2000)
                        isLoading = false
                    }
                }

                LaunchedEffect(isLoading2) {
                    if (isLoading2) {
                        delay(2000)
                        isLoading2 = false
                    }
                }

                LoadingButton(
                    content = { Text(text) },
                    progressIndicatorSide = ProgressIndicatorSide.Start,
                    isLoading = isLoading,
                    onClick = { isLoading = true },
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoadingButton(
                    content = { Text(text2) },
                    progressIndicatorSide = ProgressIndicatorSide.End,
                    loadingContent = { Text("...") },
                    horizontalArrangementSpace = 0.dp,
                    isLoading = isLoading2,
                    onClick = { isLoading2 = true },
                )
            },
        ),
        ParticleComponent(
            name = "NumberIncrementer",
            composable = {
                var v1 by remember { mutableStateOf(0) }
                var v2 by remember { mutableStateOf(100) }

                NumberIncrementer(
                    value = v1,
                    onValueChange = { v1 = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                NumberIncrementer(
                    value = v2,
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { v2 = it },
                    colors =
                        NumberIncrementerDefaults.colors(
                            background = Color.White,
                            content = Color.Black,
                            buttonBackground = Color.Black,
                            buttonContent = Color.White,
                        ),
                )
            },
        ),
        ParticleComponent(
            name = "Three State Switch",
            composable = { TabContainer(tabs = listOf("Code", "Issues", "Pull Requests")) },
        ),
        ParticleComponent(
            name = "SearchBar",
            composable = {
                var text by remember { mutableStateOf("") }
                SearchBar(
                    modifier = Modifier.padding(32.dp),
                    value = text,
                    onValueChange = { text = it },
                    onSearch = {
                        println("Search for $it")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            text = ""
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    },
                )
            },
        ),
        ParticleComponent(
            name = "OTP Input",
            composable = {
                var message by remember { mutableStateOf("Input a code!") }

                OTPInput(
                    otpLength = 4,
                    onOtpComplete = { otp ->
                        message = "OTP Complete: $otp"
                    },
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(message)
            },
        ),
        ParticleComponent(
            name = "Rating Stars",
            composable = {
                var rating by remember { mutableStateOf(3.5f) }
                var isHalfEnabled by remember { mutableStateOf(true) }
                var starSize by remember { mutableStateOf(24f) }

                RatingStars(
                    modifier = Modifier,
                    rating = rating,
                    iconSize = starSize.dp,
                    allowHalfStars = isHalfEnabled,
                    onRatingChange = { rating = it },
                )

                Spacer(modifier = Modifier.height(64.dp))

                SimpleButton(
                    content = { Text("${if (isHalfEnabled) "Disable" else "Enable"} half start") },
                    onClick = { isHalfEnabled = !isHalfEnabled },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Star Size")
                Slider(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    value = starSize,
                    onValueChange = { starSize = it },
                    valueRange = 24f..56f,
                    steps = 10,
                )
            },
        ),
        ParticleComponent(
            name = "Stepper",
            composable = {
                val steps = listOf(
                    StepperItem("Step 1", isCompleted = true),
                    StepperItem("Step 2", isActive = true),
                    StepperItem("Step 3"),
                )
                Stepper(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    steps = steps,
                )
            },
        ),
        ParticleComponent(
            name = "Expandable Card",
            composable = {
                ExpandableCard(
                    title = "Expandable Card",
                    content = {
                        Text(
                            text = "This is the content of the expandable card. You can put any composable here, like text, images, etc."
                        )
                    },
                    initiallyExpanded = false,
                    modifier = Modifier.padding(16.dp),
                )
            },
        ),
        ParticleComponent(
            name = "FacePile",
            composable = {
                FacePile(
                    items = listOf(
                        "Apple",
                        "Orange",
                        "Banana",
                        "Pineapple",
                        "Grape",
                    ),
                )
            },
        ),
        ParticleComponent(
            name = "Timeline",
            composable = {
                Timeline(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    items = listOf(
                        TimelineItem(
                            title = { Text("Event 1") },
                            description = { Text("Description for Event 1") },
                        ),
                        TimelineItem(
                            title = { Text("Event 2") },
                            description = { Text("Description for Event 2") },
                        ),
                        TimelineItem(
                            title = { Text("Event 3") },
                            description = { Text("Description for Event 3") },
                        ),
                    ),
                )
            }
        ),
    )
