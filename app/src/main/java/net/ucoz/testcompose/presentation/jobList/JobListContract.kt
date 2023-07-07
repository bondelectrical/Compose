package net.ucoz.testcompose.presentation.jobList

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState
import net.ucoz.testcompose.presentation.models.JobUI

class JobListContract {

    object Event : ViewEvent

    data class State(
        var jobList: List<JobUI> = emptyList(),
        var isLoading: Boolean = false,
        var isError: Boolean = false,
        var errorMessage: String? = null
    ) : ViewState

    object Effect : ViewSideEffect
}


