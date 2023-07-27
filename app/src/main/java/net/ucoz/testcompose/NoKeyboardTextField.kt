package net.ucoz.testcompose

import android.content.res.Resources
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import net.ucoz.testcompose.ui.theme.IconBlue
import net.ucoz.testcompose.ui.theme.TestComposeTheme

@Composable
fun NoKeyboardTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    hintText: String,

    singleLine: Boolean = true,
    withBorder: Boolean = false,
    borderColor: Color = IconBlue,
    codeButtonClick: () -> String,
    onValueChange: (String) -> Unit,

    ) {
    val r: Resources = Resources.getSystem()
    val padding16 = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16f,
        r.displayMetrics
    )
    AndroidView(
        modifier = modifier
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
            ),
        factory = { context ->
            val frameLayout = FrameLayout
                .inflate(
                    context,
                    R.layout.view_edit_text,
                    null
                ) as FrameLayout
            val textEdit =
                frameLayout.findViewById<AppCompatEditText>(R.id.edit_text)
            val btnClear =
                frameLayout.findViewById<ImageView>(R.id.btn_clear)

            val btnCode =
                frameLayout.findViewById<ImageView>(R.id.btn_code)

            textEdit.hint = hintText
            textEdit.isFocusable = true
            textEdit.isFocusableInTouchMode = true
            textEdit.showSoftInputOnFocus = false
            textEdit.inputType = InputType.TYPE_MASK_CLASS
            textEdit.setText(text.value)
            textEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    if(s.isNotEmpty()) {
                        btnClear.isVisible = true
                        btnCode.isGone = true
                    } else {
                        btnCode.isVisible = true
                        btnClear.isGone = true
                    }
//                    btnClear.isVisible = s.isNotEmpty()
                    onValueChange(s.toString())
                }
            })
            btnClear.setOnClickListener { textEdit.setText("") }
            btnCode.setOnClickListener { textEdit.setText(codeButtonClick()) }

            frameLayout
        },
        update = { view ->
            val textEdit =
                view.findViewById<AppCompatEditText>(R.id.edit_text)
            textEdit.setText(text.value)
            textEdit.setSelection(text.value.length)
            textEdit.gravity = Gravity.CENTER_VERTICAL
            textEdit.setPadding(padding16.toInt(), 0, 0, 0)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NoKeyboardTextFieldPreview() {
    TestComposeTheme {
        var firstText by rememberSaveable { mutableStateOf("") }
        NoKeyboardTextField(
            text = remember { mutableStateOf("") },
            hintText = "Barcode",
            codeButtonClick = { "AAA-123456"}
        ) {
            firstText = it
        }
    }
}