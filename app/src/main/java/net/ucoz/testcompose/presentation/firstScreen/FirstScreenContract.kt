package net.ucoz.testcompose.presentation.firstScreen

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState
import kotlin.time.Duration


class FirstScreenContract {
    sealed class Event : ViewEvent {
        object stopClicked : Event()
        object pauseClicked : Event()
        object playClicked : Event()
        object stopTimeIntervalClicked : Event()
    }

    data class State(
        var seconds: String = "00",
        var minutes: String = "00",
        var hours: String = "00",
        var timesStop: MutableList<timeStop> = mutableListOf(),
        var isPlaying: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}
data class timeStop(
    var time: String,
    var timeDuration: Duration,
    var timeTotal: String,
    var timeTotalDuration: Duration
)