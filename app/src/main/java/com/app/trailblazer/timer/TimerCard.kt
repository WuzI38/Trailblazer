package com.app.trailblazer.timer

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel

@Composable
fun TimerCard(viewModel: TimerViewModel, context: Context) {
    val cardCols = MaterialTheme.colorScheme.surface
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardCols,
        ),
    ) {
        if (viewModel.timerState.value != TimerViewModel.TimerState.NotStarted) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Timer(viewModel.time.intValue)
                Spacer(modifier = Modifier.height(32.dp))
                TimerButtons(viewModel, context)
            }
        } else {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Enter name")
                TextField(
                    value = viewModel.timerName.value,
                    onValueChange = { viewModel.timerName.value = it },
                    placeholder = { Text(text = "Nazwa pomiaru") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.timerState.value = TimerViewModel.TimerState.Running }) {
                    Text(text = "Zatwierdź")
                }
            }
        }
    }
}