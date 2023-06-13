package net.ucoz.testcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.ucoz.testcompose.R


@Composable
fun OutlinedTextFieldWithBarCode(
    modifier: Modifier = Modifier,
    isSmallWidth: Boolean,
    onValueChange: (String) -> Unit,
    value: String,
    label: String,
    hint: String,
    codeButtonClick: () -> String,
    removeButtonClick: () -> String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    Column() {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF60729E),
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 8.dp)
        )
        Card(
            modifier = modifier
                .width(if (isSmallWidth) 156.dp else 250.dp)
                .height(36.dp)
                .background(Color.Transparent, RoundedCornerShape(32.dp)),
            elevation = 4.dp,
            shape = RoundedCornerShape(32.dp),
        ) {
            CustomOutlinedTextField(
                value = value,
                modifier = modifier
                    .width(if (isSmallWidth) 156.dp else 250.dp)
                    .height(36.dp)
                    .background(Color(0xFFFFFFFF), RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color(0xFF001538),
                    fontSize = 14.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                ),
                trailingIcon = {
                    if (value.isEmpty()) {
                        IconButton(onClick = { onValueChange(codeButtonClick()) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_code),
                                contentDescription = null,
                                tint = Color(0xFFA1AFD1)
                            )
                        }
                    } else {
                        IconButton(onClick = { onValueChange(removeButtonClick()) }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_remove),
                                contentDescription = null,
                                tint = Color(0xFFA1AFD1)
                            )
                        }
                    }


                },
                onValueChange = onValueChange,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFFFFFF),
                    unfocusedBorderColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFFFFFFFF),
                    cursorColor = Color(0xFF001538)
                ),
                placeholder = { Text(text = hint, fontSize = 14.sp, color = Color(0xFFA1AFD1)) },
                keyboardActions = keyboardActions,
                enabled = enabled,
                readOnly = readOnly,
                leadingIcon = leadingIcon,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                maxLines = maxLines,
                interactionSource = interactionSource,
            )
        }


    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(
        start = 8.dp,
        top = 0.dp,
        end = 16.dp,
        bottom = 0.dp
    ),
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = if (label != null) {
            modifier
                .semantics(mergeDescendants = true) {}
                .padding(top = 8.dp)
        } else {
            modifier
        }
            .background(colors.backgroundColor(enabled).value, shape)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding,
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape,
                    )
                }
            )
        }
    ))
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldWithBarCodePreview() {
    var id by remember { mutableStateOf("") }
    var isButtonEnable by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        OutlinedTextFieldWithBarCode(
            modifier = Modifier.onFocusEvent { focusState ->
                if (focusState.hasFocus || focusState.isFocused) {
                    coroutineScope.launch {
                        delay(1)
                        bringIntoViewRequester.bringIntoView()

                    }
                }


            },
            value = id,
            isSmallWidth = false,
            onValueChange = {
                id = it
                isButtonEnable = id.isNotEmpty()

            },
            label = "Case card ID",
            hint = "ID",
            codeButtonClick = { "AAA-1234567" },
            removeButtonClick = { "" },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )


        Spacer(
            modifier = Modifier
                .size(32.dp)
                .bringIntoViewRequester(bringIntoViewRequester)
        )

    }


}