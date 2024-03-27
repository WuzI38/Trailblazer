package com.lab.trailblazer.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun TrailListItem(trailName: String, onTrailClick: (String) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onTrailClick(trailName) }
        .background(MaterialTheme.colorScheme.primary)
        .border(2.dp, MaterialTheme.colorScheme.secondary)
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = trailName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}


