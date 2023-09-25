package net.ucoz.testcompose.presentation.secondScreen

import net.ucoz.testcompose.presentation.base.ViewEvent
import net.ucoz.testcompose.presentation.base.ViewSideEffect
import net.ucoz.testcompose.presentation.base.ViewState

class SecondScreenContract {
    sealed class Event : ViewEvent {
        data class ScanBarcodeClicked(val id: String): Event()
        data class ScanSecondBarcodeClicked(val id: String): Event()
        data class ScanThirdBarcodeClicked(val id: String): Event()

    }

    data class State(
        var barcode: String = "",
        var secondBarcode: String = "AAA-123456",
        var thirdBarcode: String = "AAA-123456",
    ) : ViewState

    object Effect : ViewSideEffect
}