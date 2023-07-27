package net.ucoz.testcompose.presentation.secondScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.ucoz.testcompose.presentation.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class SecondScreenViewModel @Inject constructor():
    BaseViewModel<
            SecondScreenContract.Event,
            SecondScreenContract.State,
            SecondScreenContract.Effect>() {

    init {

    }


    override fun setInitialState() =
        SecondScreenContract.State()

    override fun handleEvents(event: SecondScreenContract.Event) {
        when (event) {
            is SecondScreenContract.Event.ScanBarcodeClicked -> {
                setState {
                    copy(
                        barcode = event.id
                    )
                }


            }

        }
    }

}