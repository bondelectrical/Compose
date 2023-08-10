package net.ucoz.testcompose.presentation.thirdScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.ui.theme.White


@Composable
fun DeleteAddButtons(
    enableDeleteButton: Boolean = true,
    enableAddButton: Boolean = true,
    enableNextButton: Boolean = true,
    onDeleteClick: () -> Unit,
    onAddClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                White
            )
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    White
                )
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RegularButton(
                text = "Delete",
                enabled = enableDeleteButton,
                isSmallButton = true
            ) {
                onDeleteClick()
            }
            RegularButton(
                text = "Add",
                enabled = enableAddButton,
                isSmallButton = true
            ) {
                onAddClick()
            }

        }
        RegularButton(
            text = "Next",
            enabled = enableNextButton,
        ) {
            onNextClick()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EncloseSetUpButtonsPreview() {
    DeleteAddButtons(
        enableDeleteButton = true,
        enableAddButton = true,
        enableNextButton = true,
        onDeleteClick = {},
        onAddClick = {},
        onNextClick = {},
    )
}