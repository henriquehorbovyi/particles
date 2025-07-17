package io.henriquehorbovyi.demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.henriquehorbovyi.demo.data.ParticleComponent
import io.henriquehorbovyi.demo.data.particles

@Composable
fun HomeScreen(onItemClick: (particleIndex: Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Demo Particles",
                    style = TextStyle(fontSize = 24.sp),
                )
            }
        }
        items(particles) { particle ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(particles.indexOf(particle)) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DemoItem(particle)
            }
        }
    }
}

@Composable
fun DemoItem(
    particle: ParticleComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = particle.name,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

