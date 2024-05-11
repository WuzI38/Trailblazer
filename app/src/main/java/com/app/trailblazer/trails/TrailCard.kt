package com.app.trailblazer.trails

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("DiscouragedApi")
@Composable
// Display trail image and description as a composable card - main page
fun TrailCard(context: Context, trail: Trail, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        val columnCol = MaterialTheme.colorScheme.surface
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(columnCol)
                .padding(16.dp)
        ) {
            // Load image based on trail name (assuming images are in drawable folder)
            val imageResId = context.resources.getIdentifier(trail.imageName, "drawable", context.packageName)

            // Display image or placeholder if image not found
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(columnCol)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                if (imageResId != 0) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                } else {
                    // Placeholder for missing image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val textCol = MaterialTheme.colorScheme.surfaceVariant

            Text(
                text = trail.trailName,
                style = MaterialTheme.typography.headlineMedium,
                color = textCol,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = trail.location,
                style = MaterialTheme.typography.headlineMedium,
                color = textCol
            )
        }
    }
}
