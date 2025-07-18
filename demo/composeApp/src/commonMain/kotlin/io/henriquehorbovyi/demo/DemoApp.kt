package io.henriquehorbovyi.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.henriquehorbovyi.demo.data.particles
import io.henriquehorbovyi.demo.ui.DemoDetailScreen
import io.henriquehorbovyi.demo.ui.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun DemoApp() {
    MaterialTheme {
        val navController = rememberNavController()

        Scaffold { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues),
            ) {
                composable("home") {
                    HomeScreen(onItemClick = { index -> navController.navigate("details/$index") })
                }

                particles.forEachIndexed { index, particle ->
                    composable(route = "details/$index") {
                        DemoDetailScreen(particle = particle)
                    }
                }
            }
        }
    }
}
