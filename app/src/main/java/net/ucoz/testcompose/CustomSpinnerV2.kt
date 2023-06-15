package net.ucoz.testcompose

import android.os.Parcelable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.versionedparcelable.ParcelField
import net.ucoz.testcompose.ui.theme.White
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomSpinnerV2(
    label: String,
    hint: String,
    values: List<String>,
    selected: String = "",
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selected) }

    var textFieledSize by remember { mutableStateOf(Size.Zero) }

    Column() {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF60729E),
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 8.dp)
        )
        androidx.compose.material.ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            CustomOutlinedTextField(
                value = selectedText,
                modifier = Modifier
                    .width(250.dp)
                    .height(36.dp)
                    .border(
                        width = 1.dp,
                        color = if (expanded) Color(0xFF0E90E4) else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp,
                        )
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        textFieledSize = layoutCoordinates.size.toSize()
                    },
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                ),
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
                    IconButton(
                        onClick = { },
                        modifier = Modifier.clearAndSetSemantics { }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_dropdown),
                            "Trailing icon for exposed dropdown menu",
                            Modifier.rotate(
                                if (expanded)
                                    180f
                                else
                                    360f
                            ),
                            tint = Color(0xFFA1AFD1)
                        )
                    }

                },
                onValueChange = {
                    selectedText = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0E90E4),
                    unfocusedBorderColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFFFFFFFF),
                    cursorColor = Color(0xFF001538)
                ),
                placeholder = {
                    Text(
                        text = hint,
                        fontSize = 14.sp,
                        color = Color(0xFFA1AFD1)
                    )
                },
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 0.dp,
                    end = 16.dp,
                    bottom = 0.dp
                ),
                enabled = false,
            )
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                values.forEach { item ->
                    androidx.compose.material.DropdownMenuItem(
                        onClick = {
                            onSelected(item)
                            selectedText = item
                            expanded = false
                        },
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = item, fontSize = 14.sp, color = Color(0xFF60729E))
                    }
                }
            }

        }


    }
}


