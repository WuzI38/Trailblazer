package com.app.trailblazer.timer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Timer(time: Int) {
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    Text(text = String.format("%02d:%02d:%02d", hours, minutes, seconds))
}