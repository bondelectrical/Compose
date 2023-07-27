package net.ucoz.testcompose.presentation.secondScreen

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState

class SecondScreenContract {
    sealed class Event : ViewEvent {
        data class ScanBarcodeClicked(val id: String): Event()

    }

    data class State(
        var barcode: String = "",
    ) : ViewState

    object Effect : ViewSideEffect
}