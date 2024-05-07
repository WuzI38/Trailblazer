package com.app.trailblazer.timer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.trailblazer.database.DatabaseHelper

@Composable
fun RecordsCard(context: Context) {
    val databaseHelper = DatabaseHelper(context)
    val records = databaseHelper.getAllRecords()
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
        LazyColumn {
            items(records) { record ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${record.name} - ${record.date} - ${record.time}")
                    Button(onClick = { databaseHelper.deleteRecord(record.id) }) {
                        Text(text = "Usuń")
                    }
                }
            }
        }
    }
}