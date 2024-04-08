package com.lab.trailblazer.ui.theme.activities

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lab.trailblazer.activities.timer.TimerViewModel

@Composable
fun TimerScreen(timerViewModel: TimerViewModel) {
    val configuration = LocalConfiguration.current
    BoxWithConstraints {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Timer(timerViewModel.time.intValue)
                TimerButtons(timerViewModel)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Timer(timerViewModel.time.intValue)
                Spacer(modifier = Modifier.height(32.dp))
                TimerButtons(timerViewModel)
            }
        }
    }
}

@Composable
fun TimerButtons(timerViewModel: TimerViewModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = timerViewModel::startTimer) {
            Text(text = "Start")
        }
        val context = LocalContext.current
        Button(onClick = { timerViewModel.stopTimer(context) }) {
            Text(text = "Stop")
        }
        Button(onClick = timerViewModel::togglePause) {
            Text(text = if (!timerViewModel.timerStopped.value) "Pause" else "Resume")
        }
    }
}

@Composable
fun Timer(time: Int) {
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    Text(text = String.format("%02d:%02d:%02d", hours, minutes, seconds))
}