package net.ucoz.testcompose

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.ui.theme.LightBlue


@Composable
fun RegularButton(modifier: Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .width(250.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.elevation(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .width(250.dp)
                .background(
                    if(enabled) {
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF2421AB),
                                Color(0xFF295AFF)
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
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun SimpleButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        enabled = enabled,
        onClick = {
            onClick()
        },
        modifier = Modifier
            .height(48.dp)
            .width(250.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.elevation(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .height(48.dp)
                .width(250.dp)
                .background(
                    if(enabled) {
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF40DDBA),
                                Color(0xFF0E90E4)
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
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }

}
