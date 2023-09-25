package net.ucoz.testcompose.presentation.widget.lazyList

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.toDp

@ExperimentalMaterialApi
@Composable
fun LazyListWithFloatingButtonV4(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,
) {
    var space by remember {
        mutableStateOf(0)
    }
    var spaceContainer by remember {
        mutableStateOf(0)
    }
    var paddingContainer by remember {
        mutableStateOf(0)
    }
    LaunchedEffect( state.layoutInfo ) {
        if (state.isScrolledToTheEnd()) {
            state.scrollToItem(state.layoutInfo.totalItemsCount - 1)
            paddingContainer.minus(200)
        } else  {
            paddingContainer = spaceContainer
        }

        Log.d("MyTag", "paddingContainer = $paddingContainer")
        Log.d("MyTag", "spaceContainer = $spaceContainer")
        Log.d("MyTag", "space = $space")
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                spaceContainer = it.size.height
            },
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            AnimatedVisibility(visible = state.isScrolledToTheEnd()) {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            space = it.size.height
                        }
                ) {
                    bottomContent()
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(paddingContainer.toDp().dp)
            ,
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                userScrollEnabled = true,
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
            }
        }

//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.BottomCenter,
//        ) {
//            AnimatedVisibility(visible = state.isScrolledToTheEnd()) {
//                Box(
//                    modifier = Modifier
//                        .onGloballyPositioned {
//                            space = it.size.height
//                        }
//                ) {
//                    bottomContent()
//                }
//            }
//        }



    }


}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }