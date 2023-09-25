package net.ucoz.testcompose.presentation.thirdScreen

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState

class ThirdScreenContract {
    sealed class Event : ViewEvent {
        object completeClicked : Event()
        object deleteClicked : Event()
        object addClicked : Event()
        object nextClicked : Event()
    }

    data class State(
        var isShowCompleteButton: Boolean = false,
        var content: MutableList<String> = getContent(),
        var isUpdateScreen: Boolean = false
    ) : ViewState

    object Effect : ViewSideEffect
}

private fun getContent(): MutableList<String> {
    var content: MutableList<String> = mutableListOf()
    for(i in 1..20) {
        content.add("Item number $i")
    }
    return content
}

