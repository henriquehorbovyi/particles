package dev.henriquehorbovyi.demo.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.henriquehorbovyi.particles.buttons.loading.LoadingButton
import dev.henriquehorbovyi.particles.buttons.loading.ProgressIndicatorSide
import dev.henriquehorbovyi.particles.buttons.simple.SimpleButton
import dev.henriquehorbovyi.particles.number.NumberIncrementer
import dev.henriquehorbovyi.particles.number.NumberIncrementerDefaults
import dev.henriquehorbovyi.particles.tab.TabContainer
import kotlinx.coroutines.delay

data class ParticleComponent(
    val name: String,
    val composable: @Composable ColumnScope.() -> Unit
)

val particles: List<ParticleComponent> = listOf(
    ParticleComponent(
        name = "Simple Button",
        composable = {
            SimpleButton(content = { Text("Click me!") })
        }
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
                onClick = { isLoading2 = true }
            )
        }
    ),
    ParticleComponent(
        name = "NumberIncrementer",
        composable = {
            var v1 by remember { mutableStateOf(0) }
            var v2 by remember { mutableStateOf(100) }

            NumberIncrementer(
                value = v1,
                onValueChange = { v1 = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            NumberIncrementer(
                value = v2,
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(8.dp),
                onValueChange = { v2 = it },
                colors = NumberIncrementerDefaults.colors(
                    background = Color.White,
                    content = Color.Black,
                    buttonBackground = Color.Black,
                    buttonContent = Color.White
                )
            )


        }
    ),
    ParticleComponent(
        name = "Three State Switch",
        composable = { TabContainer(tabs = listOf("Code", "Issues", "Pull Requests")) }
    )
)
