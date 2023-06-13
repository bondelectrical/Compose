package net.ucoz.testcompose

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Suppress("BanInlineOptIn")
//@OptIn(ExperimentalContracts::class)
//inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
//    contract { callsInPlace(action) }
//    for (index in indices) {
//        val item = get(index)
//        action(item)
//    }
//}
//
//@Suppress("BanInlineOptIn")
//@OptIn(ExperimentalContracts::class)
//inline fun <T> List<T>.fastSumBy(selector: (T) -> Int): Int {
//    contract { callsInPlace(selector) }
//    var sum = 0
//    fastForEach { element ->
//        sum += selector(element)
//    }
//    return sum
//}

@Composable
fun LazyColumnScrollbar(
    listState: LazyListState,
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
    val layoutInfo = listState.layoutInfo
    val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
    val items = layoutInfo.visibleItemsInfo
    val itemsSize = items.fastSumBy { it.size }
    val showScrollbar = items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize
    if (!enabled) {
        Box(
            modifier = Modifier.padding(
                start = startPaddingContainer,
                end = endPaddingContainer,
                top = topPaddingContainer,
                bottom = bottomPaddingContainer
            )
        ) {
            content()
        }
    } else {
        Box {
            Box(
                modifier = Modifier.padding(
                    start = startPaddingContainer,
                    end = endPaddingContainer,
                    top = topPaddingContainer,
                    bottom = bottomPaddingContainer
                )
            ) {
                content()
            }
            if (showScrollbar) {
                LazyColumnScrollbar(
                    listState = listState,
                    thickness = thickness,
                    thumbMinHeight = thumbMinHeight,
                    thumbColor = thumbColor,
                    thumbShape = thumbShape,
                    topPaddingIndicator = topPaddingIndicator,
                    bottomPaddingIndicator = bottomPaddingIndicator,
                    topPaddingContainer = topPaddingContainer,
                    bottomPaddingContainer = bottomPaddingContainer,
                    startPaddingContainer = startPaddingContainer,
                    endPaddingContainer = endPaddingContainer,
                )
            }
        }
    }
}


@Composable
private fun LazyColumnScrollbar(
    listState: LazyListState,
    thickness: Dp,
    thumbMinHeight: Float = 1f,
    thumbColor: Color,
    thumbShape: Shape = CircleShape,
    topPaddingIndicator: Dp,
    bottomPaddingIndicator: Dp,
    topPaddingContainer: Dp,
    bottomPaddingContainer: Dp,
    startPaddingContainer: Dp,
    endPaddingContainer: Dp,
) {
    var visible = listState.isScrollInProgress
    val animatable = remember {
        Animatable(0.5f)
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(visible, animatable) {
        if (visible) {
            coroutineScope.launch {
                animatable.snapTo(1f)
                delay(1000L)
                animatable.animateTo(0.5f)
            }
        }
    }

    val realFirstVisibleItem by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.firstOrNull {
                it.index == listState.firstVisibleItemIndex
            }
        }
    }

    val isStickyHeaderInAction by remember {
        derivedStateOf {
            val realIndex = realFirstVisibleItem?.index ?: return@derivedStateOf false
            val firstVisibleIndex = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
                ?: return@derivedStateOf false
            realIndex != firstVisibleIndex
        }
    }

    fun LazyListItemInfo.fractionHiddenTop() =
        if (size == 0) 0f else -offset.toFloat() / size.toFloat()

    fun LazyListItemInfo.fractionVisibleBottom(viewportEndOffset: Int) =
        if (size == 0) 0f else (viewportEndOffset - offset).toFloat() / size.toFloat()

    val normalizedThumbSizeReal by remember {
        derivedStateOf {
            listState.layoutInfo.let {
                if (it.totalItemsCount == 0) return@let 0f

                val firstItem = realFirstVisibleItem ?: return@let 0f
                val firstPartial = firstItem.fractionHiddenTop()
                val lastPartial =
                    1f - it.visibleItemsInfo.last().fractionVisibleBottom(it.viewportEndOffset)

                val realSize = it.visibleItemsInfo.size - if (isStickyHeaderInAction) 1 else 0
                val realVisibleSize = realSize.toFloat() - firstPartial - lastPartial
                realVisibleSize / it.totalItemsCount.toFloat()
            }
        }
    }

    val normalizedThumbSize by remember {
        derivedStateOf {
            normalizedThumbSizeReal.coerceAtLeast(thumbMinHeight)
        }
    }

    fun offsetCorrection(top: Float): Float {
        if (normalizedThumbSizeReal >= thumbMinHeight) return top
        val topRealMax = 1f - normalizedThumbSizeReal
        val topMax = 1f - thumbMinHeight
        return top * topMax / topRealMax
    }

    val normalizedOffsetPosition by remember {
        derivedStateOf {
            listState.layoutInfo.let {
                if (it.totalItemsCount == 0 || it.visibleItemsInfo.isEmpty()) return@let 0f

                val firstItem = realFirstVisibleItem ?: return@let 0f
                val top =
                    firstItem.run { index.toFloat() + fractionHiddenTop() } / it.totalItemsCount.toFloat()
                offsetCorrection(top)
            }
        }
    }

    BoxWithConstraints(
        Modifier
            .alpha(animatable.value)
            .fillMaxWidth()
            .padding(
                top = topPaddingIndicator + topPaddingContainer,
                bottom = bottomPaddingIndicator + bottomPaddingContainer
            )
            .fillMaxHeight()
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .graphicsLayer {
                    translationY = constraints.maxHeight.toFloat() * normalizedOffsetPosition
                }
                .padding(horizontal = (endPaddingContainer - thickness) / 2)
                .width(thickness)
                .clip(thumbShape)
                .background(thumbColor)
                .fillMaxHeight(
                    if (listState.layoutInfo.totalItemsCount == 0) {
                        0f
                    } else {
                        normalizedThumbSize
                    }
                ))
    }
}
