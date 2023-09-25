package net.ucoz.testcompose.presentation.widget.lazyList

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.toDp
import kotlin.math.abs
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun LazyListWithFloatingButtonV6(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,
) {
    var toolbarHeight by remember {
        mutableStateOf(228)
    }

    val padding = remember {
        mutableStateOf(0)
    }

    var enabled by remember {
        mutableStateOf(true)
    }

    val bottomBarHeight = toolbarHeight.toDp().dp
    val bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                padding.value = abs( bottomBarOffsetHeightPx.value.roundToInt())

                Log.d("MyTag", "padding.value = ${padding.value}")
                Log.d("MyTag", "toolbarHeight.value = ${toolbarHeight}")
                Log.d("MyTag", "toolbarHeight  dp = ${toolbarHeight.toDp().dp}")
                return Offset.Zero
            }
        }
    }




    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = toolbarHeight.toDp().dp),
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
                Button(onClick = {
                    enabled = enabled.not()
                    Log.d("MyTag", " enabled = ${ enabled}")

                }) {
                    Text(text = "Click")

                }
            }
        }
        BottomAppBar(            modifier = Modifier.height(if(enabled){toolbarHeight.toDp().dp} else {0.dp})
//            .height(toolbarHeight.minus(padding.value).toDp().dp)
        ) {
            bottomContent()
            
        }
    }

    LaunchedEffect(state.layoutInfo.totalItemsCount) {
        if (state.isScrolledToTheEnd()) {
            state.scrollToItem(state.layoutInfo.totalItemsCount - 1)
        }
    }
//    LaunchedEffect(state.layoutInfo.visibleItemsInfo) {
//        if (state.isScrolledToTheEnd()) {
//            enabled = true
//            Log.d("Mytag", "enabled = true")
//        } else {
//            enabled = false
//            Log.d("Mytag", "enabled = false")
//        }
//    }


}