package net.ucoz.testcompose.presentation.thirdScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.ui.theme.White

@Composable
fun CompleteButton(onCompleteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                White
            )
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        RegularButton(
            text = "complete",
        ) {
            onCompleteClick()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CompleteButtonPreview() {
    CompleteButton() {

    }
}