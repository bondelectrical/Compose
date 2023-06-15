package net.ucoz.testcompose

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import net.ucoz.testcompose.ui.theme.*


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TestComposeTheme {
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Bgr
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomSpinner(
                            "Reason",
                            "Choose reason",
                            listOf(
                                "Americano",
                                "Cappuccino",
                                "Espresso",
                                "Latte",
                                "Mocha",
                                "Americano",
                                "Cappuccino",
                                "Espresso",
                                "Latte",
                                "Mocha"
                            ),
                        ) {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                        Divider(modifier = Modifier.height(32.dp), color = Color.Transparent)
                        CustomSpinnerV2(
                            "Reason",
                            "Choose reason",
                            listOf(
                                "Americano",
                                "Cappuccino",
                                "Espresso",
                                "Latte",
                                "Mocha",
                                "Americano",
                                "Cappuccino",
                                "Espresso",
                                "Latte",
                                "Mocha"
                            ),
                        ) {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }

                        Divider(modifier = Modifier.height(32.dp), color = Color.Transparent)
                        Divider(modifier = Modifier.height(32.dp), color = Color.Transparent)
                        RegularButton(
                            modifier = Modifier,
                            text = "MenuButtonPreview",
                            enabled = true
                        ) {

                        }
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuSample() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Label") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

fun LazyListScope.ChildList() {
    item {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            RegularButton(
                modifier = Modifier,
                text = "Submit",
                enabled = true
            ) {

            }
        }
        Spacer(modifier = Modifier.size(32.dp))
    }

}

fun LazyListScope.ButtonInList(onSubmitClick: ()-> Unit) {
    item {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
                ){
            RegularButton(
                modifier = Modifier,
                text = "Submit",
                enabled = true
            ) {
                onSubmitClick
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
    }

}




fun hideKeyboard(activityContext: Context) {
    val imm = activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val rootView = (activityContext as Activity)
        .findViewById<View>(R.id.content).rootView
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
}


@Composable
fun FirstNoKeyboardTextField() {
    var firstText by rememberSaveable { mutableStateOf("") }
    val scanRequester = remember {
        FocusRequester()
    }
    CompositionLocalProvider(
        LocalTextInputService provides null
    ) {
        EditText(
            modifier = Modifier.focusRequester(scanRequester),
            text = firstText,
            hintText = "Barcode",
            onFocusChange = {
            },
            onValueChange = {
                Log.d("MyTag", "onValueChange")
                firstText = it
            },
            onClick = {
                firstText = ""
            },
            imeAction = ImeAction.Next,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestComposeTheme {
    }
}

internal enum class Keyboard {
    Opened, Closed
}

@Composable
internal fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}

