package com.lab.trailblazer.activities.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.viewModels
import com.lab.trailblazer.ui.theme.TrailblazerTheme
import com.lab.trailblazer.ui.theme.activities.TimerScreen

class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrailblazerTheme {
                val timerViewModel: TimerViewModel by viewModels()
                TimerScreen(timerViewModel)
            }
        }
    }
}
