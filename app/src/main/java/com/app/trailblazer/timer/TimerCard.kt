package com.app.trailblazer.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.app.trailblazer.activities.TimerViewModel

// Display the content of a timer card - text filed used to enter the measurement name or
// timer + timer buttons
@Composable
fun TimerCard(viewModel: TimerViewModel, modifier: Modifier = Modifier) {
    val cardCols = MaterialTheme.colorScheme.surface
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardCols,
        ),
    ) {
        // Display timer
        if (viewModel.timerState.value != TimerViewModel.TimerState.NotStarted) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Timer(viewModel.time.value)
                Spacer(modifier = Modifier.height(32.dp))
                TimerButtons(viewModel)
            }
        } else { // Display text field if timer is in NotStarted state
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = viewModel.timerName.value.take(30),
                    onValueChange = {
                        if (it.length <= 30) viewModel.timerName.value = it // Only update if <= 40 characters
                    },
                    placeholder = { Text(text = "Enter measurement name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.surfaceVariant,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    singleLine = true,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.submitName() }) {
                    Text(
                        text = "Submit",
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}