package com.app.trailblazer.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel

// Start, Stop and Pause buttons
@Composable
fun TimerButtons(timerViewModel: TimerViewModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = {
            timerViewModel.startTimer()
        }) {
            Text(
                text = "Start",
                color = MaterialTheme.colorScheme.background
            )
        }
        Button(onClick = { timerViewModel.stopTimer() }) {
            Text(
                text = "Stop",
                color = MaterialTheme.colorScheme.background
            )
        }
        Button(onClick = timerViewModel::togglePause) {
            Text(
                text = when (timerViewModel.timerState.value) {
                    TimerViewModel.TimerState.Running -> "Pause"
                    TimerViewModel.TimerState.Paused -> "Resume"
                    else -> "Pause"
                },
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}