package com.tlopez.feedPresentation.home

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.tlopez.corePresentation.common.ScreenBackground

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    ScreenBackground {
        Text("Test")
        Button(onClick = { viewModel.onEvent(HomeViewEvent.ClickedSettings) }) {
            Text("Settings")
        }
    }
}