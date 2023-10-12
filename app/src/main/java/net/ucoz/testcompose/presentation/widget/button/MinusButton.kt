package  net.ucoz.testcompose.presentation.widget.button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.R

@Composable
fun MinusButton(modifier: Modifier, enabled: Boolean, onClick: () -> Unit) {
    Button(
        enabled = enabled,
        onClick = {
            onClick()
        },
        modifier = modifier
            .height(24.dp)
            .width(24.dp),
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2421AB)),
        contentPadding = PaddingValues(),
    ) {
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_round_remove_24),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart),
                contentDescription = "drawable icons",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MinusButtonPreview() {
    MinusButton(modifier = Modifier, true) {

    }
}
