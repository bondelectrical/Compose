package net.ucoz.testcompose.stopWatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalTime
class StopWatchViewModel : ViewModel() {
    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer


    var seconds by mutableStateOf("00")
    var minutes by mutableStateOf("00")
    var hours by mutableStateOf("00")
    var isPlaying by mutableStateOf(false)
    var isFirstStart by mutableStateOf(false)

    fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.toDuration(DurationUnit.SECONDS))
            updateTimeStates()
        }
        isPlaying = true
        isFirstStart = true

    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@StopWatchViewModel.seconds = seconds.pad()
            this@StopWatchViewModel.minutes = minutes.pad()
            this@StopWatchViewModel.hours = hours.pad()
        }
    }

    fun pause() {
        timer.cancel()
        isPlaying = false
    }

    fun stop() {
        if(isFirstStart) {
            pause()
            time = Duration.ZERO
            updateTimeStates()
            isFirstStart = false
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    private fun Long.pad(): String {
        return this.toString().padStart(2, '0')
    }
}



