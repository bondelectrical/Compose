package net.ucoz.testcompose.presentation.widget.lazyList

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.fastSumBy
import net.ucoz.testcompose.presentation.widget.scroll.toDp
import net.ucoz.testcompose.ui.theme.DarkBlue

@SuppressLint("UnrememberedMutableState")
@Composable
fun LazyListWithFloatingButtonV2(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,

    ) {
//    val state = rememberLazyListState()

    var space by remember {
        mutableStateOf(0)
    }
    var offset by remember {
        mutableStateOf(0)
    }
    var isCalculation by remember {
        mutableStateOf(true)
    }
    val layoutInfo = remember { derivedStateOf { state.layoutInfo } }
    val viewportSize = layoutInfo.value.viewportEndOffset - layoutInfo.value.viewportStartOffset
    if (state.layoutInfo.visibleItemsInfo.isNotEmpty()) {
        Log.d("MyTag", "viewportSize ${ state.layoutInfo.visibleItemsInfo.last ().index}")
    }


    Log.d("MyTag", "layoutInfo.value.viewportEndOffset ${layoutInfo.value.visibleItemsInfo.toString()}")
    Log.d("MyTag", "layoutInfo.value.viewportStartOffset ${layoutInfo.value.viewportStartOffset}")
    val items = layoutInfo.value.visibleItemsInfo
    val itemsSize = items.fastSumBy { it.size }
    Log.d("MyTag", "itemsSize $itemsSize")
    Log.d("MyTag", "items.size ${items.size}")
    val showScrollbar =
        items.size < layoutInfo.value.totalItemsCount || itemsSize - offset > viewportSize
    Log.d("MyTag", "showScrollbar $showScrollbar")



    isCalculation = (itemsSize) < space
    Log.d("MyTag", "isCalculation $isCalculation")
//    if (isCalculation) {
//        Log.d("MyTag","offset  1111 $offset")
//        offset = if (showScrollbar) 0 else space - itemsSize.value
//
//    }
    val isChengBottomItem: MutableState<Int> = remember {
        mutableStateOf(0)
    }
    if (items.isNotEmpty()) isChengBottomItem.value = items.last().size
    LaunchedEffect(key1 = isCalculation, key2 = items.size) {
        Log.d("MyTag", "LaunchedEffect ")
        offset = if (showScrollbar) 0 else space - itemsSize - offset
    }

    Log.d("MyTag", "offset $offset")
    Log.d("MyTag", "space $space")



    LazyColumn(
        userScrollEnabled = true,
        modifier = if (enabledIndicator) {
            Modifier
                .fillMaxSize()
                .drawVerticalScrollbar(
                    state,
                    thickness = thickness,
                    topPaddingIndicator = topPaddingIndicator,
                    bottomPaddingIndicator = bottomPaddingIndicator,
                    endPaddingIndicator = endPaddingIndicator,
                )
                .onGloballyPositioned {
                    space = it.size.height
                }
//                .getUnusedVerticalSpace() {
//                    space = it.toInt()
//                }
        } else {
            Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    space = it.size.height
                }
//                .getUnusedVerticalSpace() {
//                    space = it.toInt()
//                }
        },
        state = state,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        topContent()
        ButtonInList(offset) {
            bottomContent()
        }

    }
}


private fun LazyListScope.ButtonInList(
    offset: Int,
    bottomContent: @Composable () -> Unit,
) {
    item {
        Spacer(modifier = Modifier.size((offset).toDp().dp))
        bottomContent()
    }

}



//@Preview(showBackground = true)
//@Composable
//fun LazyListWithFloatingButtonPreview() {
//    val enabledButton: Boolean = true
//    val textButton: String = "Submit"
//    val topPaddingButton: Dp = 32.dp
//    val bottomPaddingButton: Dp = 32.dp
//    LazyListWithFloatingButton(
//        bottomContent = {
//            Spacer(modifier = Modifier.size(topPaddingButton))
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                RegularButton(
//                    modifier = Modifier,
//                    enabled = enabledButton,
//                    text = textButton,
//                ) {
////                    on Submit Click
//                }
//            }
//            Spacer(modifier = Modifier.size(bottomPaddingButton))
//        },
//    ) {
//        items(20) {
//            Text(
//                text = "Please, scan Case card ID barcode",
//                fontSize = 16.sp,
//                color = DarkBlue,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp),
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//
//}