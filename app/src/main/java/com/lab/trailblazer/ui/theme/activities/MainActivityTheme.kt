package com.lab.trailblazer.ui.theme.activities

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.lab.trailblazer.R

@Composable
fun TrailList(trailNames: List<String>, onTrailClick: (String) -> Unit, onTimerClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    BoxWithConstraints {
        // Swap trailNames.size for constant 5 if there are more elements
        val itemHeight = with(LocalDensity.current) { (constraints.maxHeight / trailNames.size).toDp() }
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    items(trailNames) { trailName ->
                        TrailListItem(trailName, onTrailClick, Modifier.height(itemHeight))
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Timer",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onTimerClick() }
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(trailNames) { trailName ->
                        TrailListItem(trailName, onTrailClick, Modifier.height(itemHeight / 2))
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Timer",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable { onTimerClick() }
                )
            }
        }
    }
}


@Composable
fun TrailListItem(trailName: String, onTrailClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrailClick(trailName) }
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = trailName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp, modifier = Modifier.align(Alignment.BottomEnd))
    }
}
