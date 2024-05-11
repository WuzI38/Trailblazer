package com.app.trailblazer.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Navigation drawer item data (like home, easy, timer)
data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)