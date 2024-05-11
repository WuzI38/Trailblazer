package com.app.trailblazer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

// Application navigation bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    appBarText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    onClick: () -> Unit,
    navigationIcon: ImageVector = Icons.Default.Menu,
    contentDescription: String? = null
) {
    val detailColor = MaterialTheme.colorScheme.secondary
    TopAppBar(
        title = {
            Text(
                text = appBarText,
                color = detailColor,
                style = textStyle
            )
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = contentDescription,
                    tint = detailColor
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}