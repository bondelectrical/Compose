package net.ucoz.testcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditText(
    modifier: Modifier = Modifier,

    text: String,
    hintText: String,
    infoText: String = "",

    width: Dp = dimensionResource(R.dimen.etWidth),
    height: Dp = dimensionResource(R.dimen.etHeight),
    startPadding: Dp = dimensionResource(R.dimen.padding16),

    readOnly: Boolean = false,
    enabled: Boolean = true,
    withButton: Boolean = true,
    singleLine: Boolean = true,
    maxLengthOfNumbers: Int = 7,

    withBorder: Boolean = false,

    iconId: Int = R.drawable.ic_remove,
    iconColor: Color = MiddleBlue,
    borderColor: Color = IconBlue,

    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,

    onClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit = {},
    cursorBrush: Brush = Brush.linearGradient(
        colors = listOf(MiddleBlue, MiddleBlue)
    )
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val visualTransformation: VisualTransformation =
        if (keyboardType == KeyboardType.Number) { MaskTransformation() }
        else VisualTransformation.None

    Column(
        modifier = Modifier
            .then(modifier)
            .width(width),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (infoText.isNotBlank()) {
            Box(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_p16_t20),
                        bottom = 8.dp
                    ),

                    text = infoText,
                    textAlign = TextAlign.Left,

                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Normal,
                    fontSize = textSize12(),
                    color = MiddleBlue,
                )
            }
        }

        BasicTextField(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = if (singleLine) {
                        RoundedCornerShape(50)
                    } else RoundedCornerShape(20.dp)
                )
                .background(
                    color = Color.White,
                    shape = if (singleLine) {
                        RoundedCornerShape(50)
                    } else RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (withBorder) borderColor else Color.Transparent,
                    shape = if (singleLine) {
                        RoundedCornerShape(50)
                    } else RoundedCornerShape(20.dp)
                )
                .height(height)
                .onFocusChanged { onFocusChange(it) },
            value = text,
            textStyle = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                fontSize = textSize14(),
                color = DarkBlue
            ),
            singleLine = singleLine,
            enabled = enabled,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                Row(
                    modifier = if (singleLine) {
                        Modifier.fillMaxWidth()
                    } else Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = if (singleLine) {
                        Alignment.CenterVertically
                    } else Alignment.Top
                ) {
                    //Leading icon if need
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(start = startPadding)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = hintText,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.W400,
                                fontStyle = FontStyle.Normal,
                                fontSize = textSize14(),
                                color = LightBlue,
                            )
                        }
                        innerTextField()
                    }
                    if (withButton && text.isNotBlank()) {
                        IconButton(
                            onClick = onClick
                        ) {
                            Icon(
                                modifier = Modifier.size(dimensionResource(R.dimen.iconSize)),
                                painter = painterResource(id = iconId),
                                contentDescription = "clear",
                                tint = iconColor
                            )
                        }
                    }
                }
            },
            readOnly = readOnly,
            cursorBrush = cursorBrush,

            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            onValueChange = {
                if (keyboardType == KeyboardType.Number) {
                    if (it.length < maxLengthOfNumbers) {
                        onValueChange(it)
                    }
                } else {
                    onValueChange(it)
                }
            },
        )
    }
}

@Composable
fun EditTextBarcode(
    modifier: Modifier = Modifier,

    text: String,
    hintText: String,
    infoText: String = "",

    width: Dp = dimensionResource(R.dimen.etWidth),
    height: Dp = dimensionResource(R.dimen.etHeight),
    startPadding: Dp = dimensionResource(R.dimen.padding16),

    enabled: Boolean = true,
    withButton: Boolean = true,
    singleLine: Boolean = true,

    withBorder: Boolean = false,

    iconId: Int = R.drawable.ic_remove,
    iconColor: Color = MiddleBlue,
    borderColor: Color = IconBlue,

    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,

    onClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit = {},

    onBarcodeClick: () -> Unit = {},

    ) {

    Row(
        modifier = Modifier
            .then(modifier)
            .width(width),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        EditText(
            text = text,
            hintText = hintText,
            infoText = infoText,

            width = (
                    dimensionResource(R.dimen.etWidth) -
                            dimensionResource(R.dimen.etHeight) -
                            dimensionResource(R.dimen.padding24)
                    ),
            height = height,
            startPadding = startPadding,

            enabled = enabled,
            withButton = withButton,
            singleLine = singleLine,

            withBorder = withBorder,

            iconId = iconId,
            iconColor = iconColor,
            borderColor = borderColor,

            keyboardType = keyboardType,
            imeAction = imeAction,

            onClick = onClick,
            onDoneClick = onDoneClick,
            onValueChange = onValueChange,
            onFocusChange = onFocusChange
        )

        IconButton(
            modifier = Modifier
                .shadow(dimensionResource(R.dimen.padding_p0_t8), CircleShape)
                .size(dimensionResource(R.dimen.etHeight))
                .clip(CircleShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            StartBlueGradient,
                            EndBlueGradient
                        )
                    )
                ),
            onClick = onBarcodeClick
        ) {
            Icon(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_p8_t16)),
                painter = painterResource(id = R.drawable.ic_qr_code),
                contentDescription = "qr_code",
                tint = White
            )
        }

    }

}