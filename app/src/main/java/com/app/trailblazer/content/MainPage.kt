package com.app.trailblazer.content

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.remember

@Composable
fun MainPage(
    viewModel: MainActivityViewModel,
    context: Context,
    contentPadding: PaddingValues,
    filteredTrails: List<Trail>?
) {
    val focusManager = LocalFocusManager.current
    val textState = rememberSaveable { mutableStateOf(viewModel.name) }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
    ) {
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

        LazyColumn {
            itemsIndexed(filteredTrails ?: listOf()) { _, trail ->
                TrailCard(context = context, trail = trail, onClick = {
                    filteredTrails?.let {
                        viewModel.trailIndex.value = it.indexOf(trail)
                        viewModel.viewState.value = MainActivityViewModel.ViewState.TRAIL
                    }
                })
            }
        }
    }
}