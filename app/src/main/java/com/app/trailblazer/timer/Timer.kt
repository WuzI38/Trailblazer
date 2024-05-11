package com.app.trailblazer.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Timer(time: Int) {
    Text(text = timeString(time))
}

// Display time inside a single record
@Composable
fun TimerRecordFormat(name: String, date: String, time: Int) {
    Column {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "$date - ${timeString(time)}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

// Transform seconds into the correct time format
fun timeString(time: Int): String {
    val hours = time / 3600
    val minutes = (time % 3600) / 60
    val seconds = time % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}