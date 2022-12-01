package com.tlopez.feedPresentation.lineChart

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.*

@Composable
fun LineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        lineChartData.apply {
            dataSets.forEach { dataSet ->
                val dataSetColor = dataSet.dataType.color
                var prevPoint: Offset? = null

                dataSet.normalized().forEach {
                    println("Trying to draw normalized... $it")
                    val newPoint = Offset(
                        size.width * it.second,
                        size.height * if (it.first.isNaN()) (size.height / 2f) else it.first
                    )
                    prevPoint?.let {
                        drawLine(
                            dataSetColor,
                            it,
                            newPoint
                        )
                    }
                    drawCircle(
                        dataSetColor,
                        10f,
                        newPoint
                    )
                    prevPoint = newPoint
                }
            }
        }
    }
}