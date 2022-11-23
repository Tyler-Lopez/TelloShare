package com.tlopez.feedPresentation.feed

import androidx.compose.foundation.Image
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coil.compose.rememberImagePainter
import com.tlopez.core.architecture.EventReceiver
import java.io.File

@Composable
fun HomeScreen(viewModel: EventReceiver<FeedViewEvent>) {
    Button(onClick = { viewModel.onEvent(FeedViewEvent.TempClickedInsertChallenge) }) {
        Text("Click me")
    }
    //  Button(onClick = { viewModel.onEvent(FeedViewEvent.TempClickedTemp)}) {
    //       Text("temp button")
    //  }
}