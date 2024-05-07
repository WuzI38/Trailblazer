package com.app.trailblazer.timer

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.media.VolumeShaper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TimerScreen(timerViewModel: TimerViewModel, context: Context) {
    val configuration = LocalConfiguration.current
    BoxWithConstraints {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Timer(timerViewModel.time.intValue)
                TimerButtons(timerViewModel, context)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Timer(timerViewModel.time.intValue)
                Spacer(modifier = Modifier.height(32.dp))
                TimerButtons(timerViewModel, context)
            }
        }
    }
}
