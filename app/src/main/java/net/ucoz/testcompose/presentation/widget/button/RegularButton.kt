package net.ucoz.testcompose.presentation.widget.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.ui.theme.BlueGradientDark
import net.ucoz.testcompose.ui.theme.BlueGradientLight
import net.ucoz.testcompose.ui.theme.LightBlue
import net.ucoz.testcompose.ui.theme.White

@Composable
fun RegularButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isSmallButton: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .width(if (isSmallButton) 152.dp else 250.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp,
            disabledElevation = 8.dp,
            hoveredElevation = 8.dp,
            focusedElevation = 8.dp,
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .width(if (isSmallButton) 152.dp else 250.dp)
                .background(
                    if (enabled) {
                        Brush.horizontalGradient(
                            listOf(
                                BlueGradientLight,
                                BlueGradientDark
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            listOf(
                                LightBlue,
                                LightBlue
                            )
                        )
                    }

                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.uppercase(),
                fontSize = 16.sp,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegularButtonPreview() {
    RegularButton(
        text = "Regular Button Preview",
    ) {

    }
}