package com.app.trailblazer.content

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.app.trailblazer.activities.MainActivityViewModel
import com.app.trailblazer.trails.Trail
import com.app.trailblazer.trails.TrailCard
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

// Main page of the application
@Composable
fun MainPage(
    viewModel: MainActivityViewModel,
    context: Context,
    contentPadding: PaddingValues,
    filteredTrails: List<Trail>?
) {
    val focusManager = LocalFocusManager.current
    val textState = rememberSaveable { mutableStateOf(viewModel.name) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columns = when {
        isLandscape -> 3
        else -> 1
    }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) {
        // Search field used to filter trails
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                viewModel.name = it
            },
            label = { Text("Filter trails") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            )
        )

        // Display trail cards (images + description) as a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns)
        ) {
            itemsIndexed(filteredTrails ?: listOf()) { index, trail ->
                TrailCard(context = context, trail = trail, onClick = {
                    filteredTrails?.let {
                        viewModel.setTrailIndex(index)
                        viewModel.setTrailName(trail.trailName)
                        viewModel.viewState.value = MainActivityViewModel.ViewState.TRAIL
                    }
                })
            }
        }
    }
}