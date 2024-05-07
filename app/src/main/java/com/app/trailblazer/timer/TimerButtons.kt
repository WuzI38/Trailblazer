package com.app.trailblazer.timer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel

@Composable
fun TimerButtons(timerViewModel: TimerViewModel, context: Context) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = timerViewModel::startTimer) {
            Text(text = "Start")
        }
        Button(onClick = { timerViewModel.stopTimer(context) }) {
            Text(text = "Stop")
        }
        Button(onClick = timerViewModel::togglePause) {
            Text(text = when (timerViewModel.timerState.value) {
                TimerViewModel.TimerState.Running -> "Pause"
                TimerViewModel.TimerState.Paused -> "Resume"
                else -> "Pause"
            })
        }
    }
}