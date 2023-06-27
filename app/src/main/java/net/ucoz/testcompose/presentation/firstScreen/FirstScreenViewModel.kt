package net.ucoz.testcompose.presentation.firstScreen

import net.ucoz.testcompose.presentation.base.BaseViewModel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FirstScreenViewModel :
    BaseViewModel<
            FirstScreenContract.Event,
            FirstScreenContract.State,
            FirstScreenContract.Effect>() {


    private var time: Duration = Duration.ZERO
    private var timer: Timer? = null

    override fun setInitialState() = FirstScreenContract.State()

    override fun handleEvents(event: FirstScreenContract.Event) {
        when (event) {
            is FirstScreenContract.Event.playClicked -> {
                start()
            }

            is FirstScreenContract.Event.pauseClicked -> {
                pause()
            }

            is FirstScreenContract.Event.stopClicked -> {
                stop()
            }

            is FirstScreenContract.Event.stopTimeIntervalClicked -> {
                stopTimeInterval()
            }
        }
    }

    private fun stopTimeInterval() {
        if (time != Duration.ZERO) {
            time.toComponents { hours, minutes, seconds, _ ->
                var timeInterval: String? = null
                val timeTotal = hours.pad() + "." + minutes.pad() + "." + seconds.pad()
                var timeDuration: Duration? = null
                if (viewState.value.timesStop.isNullOrEmpty()) {
                    timeInterval = timeTotal
                    timeDuration = time
                } else {
                    timeDuration = time.minus(viewState.value.timesStop.last().timeDuration)
                    timeDuration.toComponents { hours, minutes, seconds, _ ->
                        timeInterval = hours.pad() + "." + minutes.pad() + "." + seconds.pad()
                    }
                }
                viewState.value.timesStop.add(
                    timeStop(
                        "+ $timeInterval",
                        timeDuration,
                        timeTotal,
                        time
                    )
                )
            }
        }
    }

    private fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.toDuration(DurationUnit.SECONDS))
            updateTimeStates()
        }
        setState {
            copy(isPlaying = true)
        }

    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            setState {
                copy(
                    hours = hours.pad(),
                    minutes = minutes.pad(),
                    seconds = seconds.pad()
                )
            }
        }
    }

    private fun pause() {
        timer?.cancel()
        setState {
            copy(isPlaying = false)
        }
    }

    private fun stop() {
        pause()
        time = Duration.ZERO
        updateTimeStates()
        viewState.value.timesStop.clear()
    }

}

private fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}

private fun Long.pad(): String {
    return this.toString().padStart(2, '0')
}