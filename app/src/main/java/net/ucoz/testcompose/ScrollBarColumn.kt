package net.ucoz.testcompose


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScrollbar(
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    topPaddingContainer: Dp = 0.dp,
    bottomPaddingContainer: Dp = 0.dp,
    startPaddingContainer: Dp = 16.dp,
    endPaddingContainer: Dp = 16.dp,
    thumbMinHeight: Float = 0.1f,
    thumbColor: Color = Color(0xFFA1AFD1),
    thumbShape: Shape = CircleShape,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val state = rememberLazyListState()
    LazyColumnScrollbar(
        listState = state,
        thickness = thickness,
        topPaddingIndicator = topPaddingIndicator,
        bottomPaddingIndicator = bottomPaddingIndicator,
        topPaddingContainer = topPaddingContainer,
        bottomPaddingContainer = bottomPaddingContainer,
        startPaddingContainer = startPaddingContainer,
        endPaddingContainer = endPaddingContainer,
        thumbMinHeight = thumbMinHeight,
        thumbColor = thumbColor,
        thumbShape = thumbShape,
        enabled = enabled,
    ) {
        LazyColumn(
            state = state
        ) {
            item {
                content()
            }

        }
    }

}


//@Composable
//fun ColumnScrollbar(
//    thickness: Dp = 6.dp,
//    topPaddingIndicator: Dp = 0.dp,
//    bottomPaddingIndicator: Dp = 0.dp,
//    topPaddingContainer: Dp = 0.dp,
//    bottomPaddingContainer: Dp = 0.dp,
//    startPaddingContainer: Dp = 16.dp,
//    endPaddingContainer: Dp = 16.dp,
//    thumbMinHeight: Float = 0.1f,
//    thumbColor: Color = Color(0xFFA1AFD1),
//    thumbShape: Shape = CircleShape,
//    enabled: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    BoxWithConstraints() {
//        val scrollState = rememberScrollState()
//        val viewMaxHeight = maxHeight.value
//        val columnMaxScroll = scrollState.maxValue
//        val scrollStateValue = scrollState.value
//        val paddingSize = (scrollStateValue * viewMaxHeight) / columnMaxScroll
//        val animation = animateDpAsState(targetValue = paddingSize.dp)
//        val scrollBarHeight = viewMaxHeight * 0.5f
//        Log.d("MyTag", "viewMaxHeight ${maxHeight.value.toString()}")
//        Log.d("MyTag", "scrollState.maxValue ${scrollState.maxValue.toString()}")
//
//        var visible = scrollState.isScrollInProgress
//        val animatable = remember {
//            Animatable(0.5f)
//        }
//        val coroutineScope = rememberCoroutineScope()
//        LaunchedEffect(visible, animatable) {
//            if (visible) {
//                coroutineScope.launch {
//                    animatable.snapTo(1f)
//                    delay(1000L)
//                    animatable.animateTo(0.5f)
//                }
//            }
//        }
//
//        if (!enabled) {
//            Box(
//                modifier = Modifier.padding(
//                    start = startPaddingContainer,
//                    end = endPaddingContainer,
//                    top = topPaddingContainer,
//                    bottom = bottomPaddingContainer
//                )
//            ) {
//                content()
//            }
//        } else {
//            Row() {
//                BoxWithConstraints(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .weight(1f)
//                        .verticalScroll(state = scrollState)
//                ) {
//                    content()
//                }
//                if (columnMaxScroll > 0) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .width(endPaddingContainer)
//                            .paddingFromBaseline(animation.value)
//                            .padding(
//                                top = topPaddingIndicator + topPaddingContainer,
//                                bottom = bottomPaddingIndicator + bottomPaddingContainer,
//                                start = (endPaddingContainer - thickness) / 2,
//                                end = (endPaddingContainer - thickness) / 2
//                            )
//                            .alpha(animatable.value)
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .height(scrollBarHeight.dp)
//                                .width(thickness)
//                                .background(color = thumbColor, shape = thumbShape)
//                        )
//                    }
//                }
//
//            }
//        }
//    }
//}

@Preview(widthDp = 400, heightDp = 400, showBackground = true)
@Composable
fun ColumnScrollbarPreview() {
    val state = rememberScrollState()
    ColumnScrollbar() {
        Column(
            modifier = Modifier
                .verticalScroll(state),
        ) {
            repeat(50) {
                Text(
                    text = "Item ${it + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }

}