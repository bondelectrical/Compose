package net.ucoz.testcompose.presentation.thirdScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import net.ucoz.testcompose.presentation.thirdScreen.composables.CompleteButton
import net.ucoz.testcompose.presentation.thirdScreen.composables.DeleteAddButtons
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButton
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV2
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV3
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.ui.theme.DarkBlue



data class ListItem(val text: String)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirdScreen(
    state: ThirdScreenContract.State,
    effectFlow: Flow<ThirdScreenContract.Effect>?,
    onEventSent: (event: ThirdScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: ThirdScreenContract.Effect) -> Unit
) {

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->

        }?.collect()
    }

//    ListWithButtonScreen(state, onEventSent)
//    val state = rememberLazyListState()
//    LazyListWithFloatingButtonV3(
//        header = {DeleteAddButtons(
//                        onDeleteClick = { onEventSent(ThirdScreenContract.Event.deleteClicked) },
//                        onAddClick = { onEventSent(ThirdScreenContract.Event.addClicked) },
//                        onNextClick = { onEventSent(ThirdScreenContract.Event.nextClicked)}
//                    )},
//        body = {LazyColumn(
//            modifier = Modifier.drawVerticalScrollbar(state),
//            state = state
//        ) {
//            items(20) {
//                Text(
//                    text = "Item ${it + 1}",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    fontSize = 16.sp,
//                    color = Color(0xFF001538),
//                )
//            }
//        }}
//    )


    LazyListWithFloatingButtonV3(
        bottomContent = {
//            DeleteAddButtons(
//                onDeleteClick = { onEventSent(ThirdScreenContract.Event.deleteClicked) },
//                onAddClick = { onEventSent(ThirdScreenContract.Event.addClicked) },
//                onNextClick = { onEventSent(ThirdScreenContract.Event.nextClicked)}
//            )
            when {
                state.isShowCompleteButton -> {
                    CompleteButton {
                        onEventSent(ThirdScreenContract.Event.completeClicked)
                    }
                }

                else -> {
                    DeleteAddButtons(
                        onDeleteClick = { onEventSent(ThirdScreenContract.Event.deleteClicked) },
                        onAddClick = { onEventSent(ThirdScreenContract.Event.addClicked) },
                        onNextClick = { onEventSent(ThirdScreenContract.Event.nextClicked)}
                    )
                }

            }
        },
    ) {
        items(state.content.size) {
            Text(
                text = state.content[it],
                fontSize = 16.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
fun ListWithButtonScreen(state: ThirdScreenContract.State, onEventSent: (event: ThirdScreenContract.Event) -> Unit,) {
    // Генерируем список элементов
//    val itemList = List(5) { index -> ListItem("Item $index") }

    // Композиция экрана
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Список элементов с прокруткой
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(state.content.size) { item ->
                Text(
                    text = state.content[item],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        // Кнопка, привязанная к низу экрана
        Button(
            onClick = {
                onEventSent(ThirdScreenContract.Event.addClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(16.dp)
        ) {
            Text(text = "Кнопка")
        }
    }
}

