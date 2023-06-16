package net.ucoz.testcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomSpinnerV3(
    label: String,
    hint: String,
    values: List<String>,
    selected: String = "",
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selected) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Column() {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF60729E),
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 8.dp)
        )
        Column() {
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
                            bottomStart = if (expanded) 0.dp else 20.dp,
                            bottomEnd = if (expanded) 0.dp else 20.dp,
                        )
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        textFieldSize = layoutCoordinates.size.toSize()
                    },
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = if (expanded) 0.dp else 20.dp,
                    bottomEnd = if (expanded) 0.dp else 20.dp,
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
                        onClick = {
                            expanded = expanded.not()
                        },
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
            AnimatedVisibility(
                visible = expanded,
            ) {
                Popup() {
                    val state = rememberLazyListState()
                    LazyColumn(
                        modifier = Modifier
                            .drawVerticalScrollbar(
                                state,
                                thickness = 4.dp,
                                topPaddingIndicator = 8.dp,
                                bottomPaddingIndicator = 8.dp
                            )
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                            .height(150.dp)
                            .background(
                                Color.White, shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp,
                                )
                            ),
                        state = state
                    ) {
                        items(count = values.size) { index ->
                            Text(
                                text = values[index],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clickable {
                                        onSelected(values[index])
                                        selectedText = values[index]
                                        expanded = false
                                    },
                                fontSize = 14.sp,
                                color = Color(0xFF60729E),
                            )
                        }
                    }

                }

            }

        }


    }
}