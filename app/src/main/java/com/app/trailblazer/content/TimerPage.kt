package com.app.trailblazer.content

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel
import com.app.trailblazer.timer.RecordsCard
import com.app.trailblazer.timer.TimerCard

// Timer page consists of two cards - timer card (timer, buttons, measurement name text field)
// and records card - timer database records
@Composable
fun TimerPage(viewModel: TimerViewModel, context: Context) {
    val orientation = LocalConfiguration.current.orientation
    val isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimerCard(viewModel, Modifier.weight(1f))
                RecordsCard(viewModel, context, Modifier.weight(1f))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                TimerCard(viewModel, Modifier.weight(1f))
                Spacer(modifier = Modifier.height(16.dp))
                RecordsCard(viewModel, context, Modifier.weight(1f))
            }
        }
    }
}
