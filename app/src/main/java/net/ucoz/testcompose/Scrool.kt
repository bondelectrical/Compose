package net.ucoz.testcompose


import android.content.res.Resources
import android.view.ViewConfiguration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.ucoz.testcompose.ui.theme.LightBlue
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}

@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
inline fun <T> List<T>.fastSumBy(selector: (T) -> Int): Int {
    contract { callsInPlace(selector) }
    var sum = 0
    fastForEach { element ->
        sum += selector(element)
    }
    return sum
}

fun Modifier.drawVerticalScrollbar(
    state: ScrollState,
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 0.dp,
    thumbColor: Color = Color(0xFFA1AFD1),
): Modifier = composed {
    var visible = state.isScrollInProgress
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
    DrawScrollbarColumn(
        state,
        thickness,
        topPaddingIndicator,
        bottomPaddingIndicator,
        endPaddingIndicator,
        thumbColor,
        animatable.value
    )
}

fun Modifier.drawVerticalScrollbar(
    state: LazyListState,
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 0.dp,
    thumbColor: Color = Color(0xFFA1AFD1),
): Modifier = composed {
    var visible = state.isScrollInProgress
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
    DrawScrollbarLazyColumn(
        state,
        thickness,
        topPaddingIndicator,
        bottomPaddingIndicator,
        endPaddingIndicator,
        thumbColor,
        animatable.value
    )
}


private class DrawScrollbarColumn(
    private val state: ScrollState,
    private val thickness: Dp,
    private val topPaddingIndicator: Dp,
    private val bottomPaddingIndicator: Dp,
    private val endPaddingIndicator: Dp,
    private val thumbColor: Color,
    private val alpha: Float
) : DrawModifier {
    private val showScrollbar = state.maxValue > 0

    override fun ContentDrawScope.draw() {
        drawContent()
        if (showScrollbar) {
            drawScrollbar()
        }

    }

    private fun ContentDrawScope.drawScrollbar() {
        val canvasSize =
            if (size.height - (bottomPaddingIndicator.toPx() + topPaddingIndicator.toPx()) <= 0) {
                size.height
            } else {
                size.height - bottomPaddingIndicator.toPx() - topPaddingIndicator.toPx()
            }
        val totalSize = canvasSize + state.maxValue
        val thumbSize = canvasSize / totalSize * canvasSize
        val startOffset =
            state.value / totalSize * canvasSize + if (size.height - (bottomPaddingIndicator.toPx() + topPaddingIndicator.toPx()) <= 0) {
                0f
            } else {
                topPaddingIndicator.toPx()
            }
        val topLeft = Offset(
            size.width - thickness.toPx() - if ((size.width - thickness.toPx()) < endPaddingIndicator.toPx()) 0f else endPaddingIndicator.toPx(),
            startOffset
        )
        val size = Size(thickness.toPx(), thumbSize)

        drawRoundRect(
            color = thumbColor,
            topLeft = topLeft,
            size = size,
            cornerRadius = CornerRadius(thickness.toPx() / 2, thickness.toPx() / 2),
            alpha = alpha

        )

    }
}

private class DrawScrollbarLazyColumn(
    private val state: LazyListState,
    private val thickness: Dp,
    private val topPaddingIndicator: Dp,
    private val bottomPaddingIndicator: Dp,
    private val endPaddingIndicator: Dp,
    private val thumbColor: Color,
    private val alpha: Float
) : DrawModifier {
    private val layoutInfo = state.layoutInfo
    private val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
    private val items = layoutInfo.visibleItemsInfo
    private val itemsSize = items.fastSumBy { it.size }
    private val showScrollbar = items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize

    override fun ContentDrawScope.draw() {
        drawContent()
        if (showScrollbar) {
            drawScrollbar()
        }

    }

    private fun ContentDrawScope.drawScrollbar() {
        val estimatedItemSize = if (items.isEmpty()) 0f else itemsSize.toFloat() / items.size
        val totalSize = estimatedItemSize * layoutInfo.totalItemsCount
        val canvasSize =
            if (size.height - (bottomPaddingIndicator.toPx() + topPaddingIndicator.toPx()) <= 0) {
                size.height
            } else {
                size.height - bottomPaddingIndicator.toPx() - topPaddingIndicator.toPx()
            }
        val thumbSize = viewportSize / totalSize * canvasSize
        val startOffset = if (items.isEmpty()) 0f else items
            .first()
            .run {
                (estimatedItemSize * index - offset) / totalSize * canvasSize
            }  + if (size.height - (bottomPaddingIndicator.toPx() + topPaddingIndicator.toPx()) <= 0) {
            0f
        } else {
            topPaddingIndicator.toPx()
        }
        val topLeft = Offset(
            size.width - thickness.toPx() - if ((size.width - thickness.toPx()) < endPaddingIndicator.toPx()) 0f else endPaddingIndicator.toPx(),
            startOffset
        )
        val size = Size(thickness.toPx(), thumbSize)

        drawRoundRect(
            color = thumbColor,
            topLeft = topLeft,
            size = size,
            cornerRadius = CornerRadius(thickness.toPx() / 2, thickness.toPx() / 2),
            alpha = alpha

        )

    }
}


fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()


@Preview(widthDp = 400, heightDp = 400, showBackground = true)
@Composable
fun LazyColumnScrollbarPreview() {
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .drawVerticalScrollbar(state)
            .verticalScroll(state),
    ) {
        repeat(30) {
            Text(
                text = "Item ${it + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 16.sp,
                color = Color(0xFF001538),
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 400, showBackground = true)
@Composable
fun LazyListScrollbarPreview() {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.drawVerticalScrollbar(state),
        state = state
    ) {
        items(50) {
            Text(
                text = "Item ${it + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 16.sp,
                color = Color(0xFF001538),
            )
        }
    }
}