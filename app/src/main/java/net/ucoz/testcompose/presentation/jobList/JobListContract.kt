package net.ucoz.testcompose.presentation.jobList

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState

class JobListContract {

    object Event : ViewEvent

    data class State(
        var jobList: List<JobUI> = emptyList()
    ) : ViewState

    object Effect : ViewSideEffect
}

data class JobUI(
    var customerName: String? = null,
    var activity: String? = null,
    var grid: String? = null,
    var fileName: String? = null,
    var totalTime: String? = null,
    var isPause: Boolean = true,
    var isCompleted: Boolean = true,
    var pauseName: String? = null,
)