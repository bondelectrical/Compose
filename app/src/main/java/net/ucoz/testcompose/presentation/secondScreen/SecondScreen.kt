package net.ucoz.testcompose.presentation.secondScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.ucoz.testcompose.NoKeyboardTextField
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.presentation.widget.textFieled.NoKeyboardTextFieldV2
import net.ucoz.testcompose.ui.theme.Background
import net.ucoz.testcompose.ui.theme.DarkBlue

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun SecondScreen(
    state: SecondScreenContract.State,
    effectFlow: Flow<SecondScreenContract.Effect>?,
    onEventSent: (event: SecondScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: SecondScreenContract.Effect) -> Unit
) {

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->

        }?.collect()
    }


    var isButtonEnable by remember {
        mutableStateOf(false)
    }
    var firstText =  rememberSaveable { mutableStateOf(state.barcode) }
    var secondText = remember { mutableStateOf(state.secondBarcode) }
    var thirdText = rememberSaveable { mutableStateOf(state.thirdBarcode) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    firstText.value = state.barcode
    secondText.value = state.secondBarcode
    thirdText.value = state.thirdBarcode
//    LaunchedEffect(state.secondBarcode) {
//        secondText.value = state.secondBarcode
//        Log.d("MyTAg", "state.secondBarcode = ${state.secondBarcode}")
//    }

    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Please, scan Case card ID barcode",
                fontSize = 16.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(32.dp))
            NoKeyboardTextField(
                text = firstText,
                hintText = "Barcode",
                codeButtonClick = { "AAA-12345689"}
            ) {
                firstText.value = it
                if (it.isNotEmpty()) {
                    onEventSent(
                        SecondScreenContract.Event.ScanBarcodeClicked(it)
                    )
                }
                firstText.value = ""

            }
            Spacer(modifier = Modifier.size(32.dp))
            NoKeyboardTextField(
                text = secondText,
                hintText = "Second Barcode",
                codeButtonClick = { "AAA-12345689"}
            ) {
//                secondText.value = it
                onEventSent(
                    SecondScreenContract.Event.ScanSecondBarcodeClicked(it)
                )

            }
            Spacer(
                modifier = Modifier
                    .size(32.dp)
            )
            NoKeyboardTextField(
                text = thirdText,
                hintText = "Second Barcode",
                isKeyBoardEnable = true,
                codeButtonClick = { "AAA-12345689"}
            ) {
                thirdText.value = it
//                if (it.isNotEmpty()) {
//                    onEventSent(
//                        SecondScreenContract.Event.ScanSecondBarcodeClicked(it)
//                    )
//                }
                onEventSent(
                    SecondScreenContract.Event.ScanThirdBarcodeClicked(it)
                )

            }
            Spacer(
                modifier = Modifier
                    .size(32.dp)
            )
            RegularButton(
                modifier = Modifier,
                text = "Submit",
                enabled = true
            ) {
//                if(state.barcode.isNullOrEmpty()) {
//                    Toast.makeText(context, "barcode is empty",Toast.LENGTH_SHORT).show()
//
//                } else {
//                    Toast.makeText(context, state.barcode,Toast.LENGTH_SHORT).show()
//
//                }
                Toast.makeText(context, "barcode = ${state.barcode} \n secondBarcode = ${state.secondBarcode}\n thirdBarcode = ${state.thirdBarcode}",Toast.LENGTH_SHORT).show()

            }
            Spacer(
                modifier = Modifier
                    .size(32.dp)
            )
            RegularButton(
                modifier = Modifier,
                text = "Change state",
                enabled = true
            ) {
                onEventSent(SecondScreenContract.Event.ScanBarcodeClicked("12345678"))
                onEventSent(SecondScreenContract.Event.ScanSecondBarcodeClicked("98765"))
                onEventSent(SecondScreenContract.Event.ScanThirdBarcodeClicked("4321"))
//                firstText.value = "12345678"
//                secondText.value = "98765"
//                thirdText.value = "4321"

            }
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Red),
                text = "${state.barcode}\n${state.secondBarcode}\n${state.thirdBarcode}",
                maxLines = 3,

                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = DarkBlue,
            )
            Spacer(
                modifier = Modifier
                    .size(240.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(240.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(240.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(240.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenSuccessPreview() {
    SecondScreen(
        state = SecondScreenContract.State(),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}