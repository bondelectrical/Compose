package net.ucoz.testcompose.presentation.widget.lazyList

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import kotlin.math.roundToInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@ExperimentalMaterialApi
@Composable
fun LazyListWithFloatingButtonV3(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,
) {
    Column() {
        var space by remember {
            mutableStateOf(0)
        }
        var isVisible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = state.layoutInfo.totalItemsCount, space) {
            if (state.isScrolledToTheEnd()) {
                state.scrollToItem(state.layoutInfo.totalItemsCount - 1)
            }

        }
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
            } else {
                Modifier
                    .fillMaxSize()
                    .weight(1F)
            },
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            topContent()
        }
        AnimatedVisibility(visible = state.isScrolledToTheEnd()) {
            Box(modifier = Modifier
                .onGloballyPositioned {
                    space = it.size.height
                }) {
                bottomContent()
            }
        }
    }


}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1