package net.ucoz.testcompose.presentation.widget.lazyList

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.fastSumBy
import net.ucoz.testcompose.presentation.widget.scroll.toDp

@SuppressLint("UnrememberedMutableState")
@Composable
fun LazyListWithFloatingButtonV7(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,

    ) {

    var heightContent by remember {
        mutableStateOf(0)
    }
    var heightBottomContent by remember {
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
        Log.d("MyTag", "viewportSize ${state.layoutInfo.visibleItemsInfo.last().index}")
    }
    Log.d("MyTag", " state.layoutInfo.viewportSize =  ${ state.layoutInfo.viewportSize.height.toDp()}")



    LaunchedEffect(state.layoutInfo.totalItemsCount) {
        if(heightContent - state.layoutInfo.viewportSize.height - offset < 0) {
            if(heightContent != state.layoutInfo.viewportSize.height) {
                offset = 0
            }
        } else{
            offset = heightContent - state.layoutInfo.viewportSize.height
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                heightContent = it.size.height
                Log.d("MyTag", "heightContent =  ${heightContent.toDp()}")
            }
    ) {
        LazyColumn(
            modifier = if (enabledIndicator) {
                Modifier
                    .drawVerticalScrollbar(
                        state,
                        thickness = thickness,
                        topPaddingIndicator = topPaddingIndicator,
                        bottomPaddingIndicator = bottomPaddingIndicator,
                        endPaddingIndicator = endPaddingIndicator,
                    )
            } else {
                Modifier
            },
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            topContent()
            item {
                Spacer(modifier = Modifier.size((offset).toDp().dp))
            }
            item {
                Box(
                    modifier = Modifier
                    .onGloballyPositioned {
                        heightBottomContent = it.size.height
                    }
                ) {
                    bottomContent()
                }

            }
        }

    }












//    Log.d(
//        "MyTag",
//        "layoutInfo.value.viewportEndOffset ${layoutInfo.value.visibleItemsInfo.toString()}"
//    )
//    Log.d("MyTag", "layoutInfo.value.viewportStartOffset ${layoutInfo.value.viewportStartOffset}")
//    val items = layoutInfo.value.visibleItemsInfo
//    val itemsSize = items.fastSumBy { it.size }
//    Log.d("MyTag", "itemsSize $itemsSize")
//    Log.d("MyTag", "items.size ${items.size}")
//    val showScrollbar =
//        items.size < layoutInfo.value.totalItemsCount || itemsSize - offset > viewportSize
//    Log.d("MyTag", "showScrollbar $showScrollbar")
//
//
//
//    isCalculation = (itemsSize) < space
//    Log.d("MyTag", "isCalculation $isCalculation")
////    if (isCalculation) {
////        Log.d("MyTag","offset  1111 $offset")
////        offset = if (showScrollbar) 0 else space - itemsSize.value
////
////    }
//    val isChengBottomItem: MutableState<Int> = remember {
//        mutableStateOf(0)
//    }
//    if (items.isNotEmpty()) isChengBottomItem.value = items.last().size
//    LaunchedEffect(key1 = isCalculation, key2 = items.size) {
//        Log.d("MyTag", "LaunchedEffect ")
//        offset = if (showScrollbar) 0 else space - itemsSize - offset
//    }
//
//    Log.d("MyTag", "offset $offset")
//    Log.d("MyTag", "space $space")


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