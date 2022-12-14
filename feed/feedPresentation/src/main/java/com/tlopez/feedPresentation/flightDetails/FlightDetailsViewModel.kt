package com.tlopez.feedPresentation.flightDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tlopez.core.architecture.BaseRoutingViewModel
import com.tlopez.core.ext.doOnSuccess
import com.tlopez.datastoreDomain.models.TelloFlightData
import com.tlopez.datastoreDomain.usecase.GetTelloFlightData
import com.tlopez.feedPresentation.FeedDestination
import com.tlopez.feedPresentation.FeedDestination.NavigateUp
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.ClickedNavigateUp
import com.tlopez.feedPresentation.lineChart.DataSet
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart
import com.tlopez.feedPresentation.lineChart.DataTypeLineChart.*
import com.tlopez.feedPresentation.lineChart.LineChartData
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewEvent.*
import com.tlopez.feedPresentation.quadrantGraph.PositionData
import com.tlopez.feedPresentation.quadrantGraph.Velocity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(
    private val getTelloFlightData: GetTelloFlightData,
    savedStateHandle: SavedStateHandle
) : BaseRoutingViewModel<FlightDetailsViewState, FlightDetailsViewEvent, FeedDestination>() {

    companion object {
        private val defaultDataTypeLineChart = setOf(
            ACCELERATION_X,
            ACCELERATION_Y,
            ACCELERATION_Z
        )
    }

    private lateinit var flightData: List<TelloFlightData>
    private val flightId: String = savedStateHandle["flight_id"] ?: error("Missing flight_id")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTelloFlightData(flightId)
                .doOnSuccess {
                    flightData = it
                    println("flight data is of size ${flightData.size}")
                    flightData.forEach {
                        println("here, y is ${it.y} x is ${it.x}")
                    }


                    FlightDetailsViewState(
                        lineChartData = flightData.run {
                            LineChartData(
                                dataSet = dataSetByDataType(ACCELERATION_Z),
                                rangeMaximum = maxOf(TelloFlightData::getTimeSinceStartMs).toFloat(),
                                rangeMinimum = minOf(TelloFlightData::getTimeSinceStartMs).toFloat()
                            )
                        },
                        positionData = PositionData(flightData.sortedBy { it.timeSinceStartMs }.map {
                            println("Here, time since ${it.timeSinceStartMs} vgx: ${it.vgx}")
                            Velocity(
                                it.vgx,
                                it.vgy,
                                it.timeSinceStartMs
                            )
                        }),
                        selectedDataTypeLineChart = defaultDataTypeLineChart
                    ).push()
                }
        }
    }

    override fun onEvent(event: FlightDetailsViewEvent) {
        when (event) {
            is ClickedNavigateUp -> onClickedNavigateUp()
            is ToggledDataTypeLineChart -> onToggledDataTypeLineChart(event)
        }
    }

    private fun onClickedNavigateUp() {
        routeTo(NavigateUp)
    }

    private fun onToggledDataTypeLineChart(event: ToggledDataTypeLineChart) {
        lastPushedState?.run {
            val newSelections = selectedDataTypeLineChart.toMutableSet().apply {
                if (contains(event.type)) remove(event.type) else add(event.type)
            }
            copy(
                lineChartData = lineChartData.copy(
                    dataSet = flightData.dataSetByDataType(event.type)
                )
            )
        }?.push()
    }

    private fun List<TelloFlightData>.dataSetByDataType(type: DataTypeLineChart): DataSet {
        val selector: (TelloFlightData) -> Double = {
            when (type) {
                ACCELERATION_X -> it.agx
                ACCELERATION_Y -> it.agy
                ACCELERATION_Z -> it.agz
                BATTERY_PERCENT -> it.bat.toDouble()
                SPEED_X -> it.vgx.toDouble()
                SPEED_Y -> it.vgy.toDouble()
                SPEED_Z -> it.vgz.toDouble()
                X -> it.x.toDouble()
                Y -> it.y.toDouble()
                Z -> it.z.toDouble()
            }
        }
        return DataSet(
            dataMaximum = maxOf(selector).toFloat(),
            dataMinimum = minOf(selector).toFloat(),
            dataPoints = map { selector(it).toFloat() to it.timeSinceStartMs.toFloat() },
            dataType = type
        )
    }
}