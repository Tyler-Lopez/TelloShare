package com.tlopez.feedPresentation.home

import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.*
import com.tlopez.feedPresentation.home.HomeViewEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseRoutingViewModel<HomeViewState, HomeViewEvent, FeedDestination>() {
    override fun onEvent(event: HomeViewEvent) {
        when (event) {
            is ClickedSettings -> onClickedSettings()
        }
    }

    private fun onClickedSettings() {
        routeTo(NavigateSettings)
    }

}