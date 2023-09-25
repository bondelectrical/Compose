package net.ucoz.testcompose.presentation.thirdScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV4
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV5
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV6
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV7
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButtonV8
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.toDp
import net.ucoz.testcompose.ui.theme.DarkBlue
import kotlin.math.abs
import kotlin.math.roundToInt


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


//    val toolbarHeight = 48.dp
//    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
//// our offset to collapse toolbar
//    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
//    val padding = remember {
//        mutableStateOf(0)
//    }
//// now, let's create connection to the nested scroll system and listen to the scroll
//// happening inside child LazyColumn
//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
//                val delta = available.y
//                val newOffset = toolbarOffsetHeightPx.value + delta
//                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
//                // here's the catch: let's pretend we consumed 0 in any case, since we want
//                // LazyColumn to scroll anyway for good UX
//                // We're basically watching scroll without taking it
//                Log.d("Mytag", "available = ${abs( toolbarOffsetHeightPx.value.roundToInt()).toDp()}  Dp")
//                padding.value = abs( toolbarOffsetHeightPx.value.roundToInt())
//                Log.d("Mytag", "padding.value = ${padding.value.toDp().dp}  Dp")
//                return Offset.Zero
//            }
//        }
//    }
//    Box(
//        Modifier
//            .fillMaxSize()
//            // attach as a parent to the nested scroll system
//            .nestedScroll(nestedScrollConnection)
//    ) {
//        // our list with build in nested scroll support that will notify us about its scroll
//        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
//            items(50) { index ->
//                Text("I'm item $index", modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp))
//            }
//        }
//        TopAppBar(
//            modifier = Modifier
//                .height(toolbarHeight.minus(padding.value.toDp().dp))
////                .padding(bottom = padding.value.toDp())
////                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
//            ,
//            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
//        )
//    }

    LazyListWithFloatingButtonV8(
        bottomContent = {
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

