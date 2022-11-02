package com.tlopez.navigation.router

import androidx.navigation.NavController
import com.tlopez.core.architecture.Router
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.*
import com.tlopez.navigation.Screen

data class FeedRouter(private val navController: NavController) : Router<FeedDestination> {
    override fun routeTo(destination: FeedDestination) {
        when (destination) {
            is NavigateFly -> onNavigateFly()
            is NavigateSettings -> onNavigateSettings()
        }
    }

    private fun onNavigateFly() {
        navController.navigate(Screen.Controller.route)
    }

    private fun onNavigateSettings() {
        navController.navigate(Screen.Settings.route)
    }
}