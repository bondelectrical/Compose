package net.ucoz.testcompose.presentation.widget.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.presentation.widget.button.MinusButton
import net.ucoz.testcompose.presentation.widget.button.PlusButton
import net.ucoz.testcompose.presentation.widget.scroll.toDp
import net.ucoz.testcompose.textSize14

@Composable
fun DefinitionOfPadding() {
    var padding by remember { mutableStateOf(0.dp) }
    var space by remember { mutableStateOf(0.dp) }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .padding(start = padding)
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.Red)

        )
        Box(
            modifier = Modifier
                .padding(end = padding)
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.Green)

        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .onGloballyPositioned {
                space = it.size.width.toDp().dp
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MinusButton(modifier = Modifier.padding(start = 16.dp), padding > 0.dp) {
                padding = padding.minus(2.dp)

            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "$padding",
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = textSize14(),
                color = Color.Black,
            )
            PlusButton(modifier = Modifier.padding(end = 16.dp), padding < (space/2 - 1.dp)) {
                padding = padding.plus(2.dp)
            }
        }


    }
}