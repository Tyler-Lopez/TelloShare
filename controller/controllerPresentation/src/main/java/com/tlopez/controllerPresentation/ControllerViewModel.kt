package com.tlopez.controllerPresentation

import androidx.lifecycle.viewModelScope
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.controllerPresentation.ControllerViewState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val telloRepository: TelloRepository
) : BaseRoutingViewModel<ControllerViewState, ControllerViewEvent, ControllerDestination>() {

    companion object {
        private const val DELAY_MS_HEALTH_CHECK = 500L
    }

    private var healthCheckJob: Job? = null

    init {
        Connecting.push()
        healthCheckLoop()
    }

    override fun onEvent(event: ControllerViewEvent) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        super.onCleared()
        healthCheckJob?.cancel()
    }

    private fun healthCheckLoop() {
        healthCheckJob?.cancel()
        healthCheckJob = viewModelScope.launch(Dispatchers.IO) {
            println("here healthy check")
            healthCheckAction()
            delay(DELAY_MS_HEALTH_CHECK)
            healthCheckLoop()
        }
    }

    private suspend fun healthCheckAction() {
        val b = telloRepository.connect()
        println(b)
    }
}