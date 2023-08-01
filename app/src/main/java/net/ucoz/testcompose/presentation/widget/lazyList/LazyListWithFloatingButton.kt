package net.ucoz.testcompose.presentation.widget.lazyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
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

@Composable
fun LazyListWithFloatingButton(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit
) {
    val state = rememberLazyListState()
    var space by remember {
        mutableStateOf(0)
    }
    var offset by remember {
        mutableStateOf(0)
    }

    val layoutInfo = state.layoutInfo
    val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
    val items = layoutInfo.visibleItemsInfo
    val itemsSize = items.fastSumBy { it.size }
    val showScrollbar =
        items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize
    if ((itemsSize + offset) < space) {
        offset = if (showScrollbar) 0 else space - itemsSize

    }
    LazyColumn(
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
                .getUnusedVerticalSpace() {
                    space = it.toInt()
                }
        } else {
            Modifier
                .fillMaxSize()
                .getUnusedVerticalSpace() {
                    space = it.toInt()
                }
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

private fun Modifier.getUnusedVerticalSpace(
    getSpace: (space: Float) -> Unit,
) = composed {
    GetUnusedVerticalSpace() {
        getSpace(it)
    }


}

private class GetUnusedVerticalSpace(
    private val getSpace: (space: Float) -> Unit,
) : DrawModifier {
    override fun ContentDrawScope.draw() {
        getSpace(size.height)
        drawContent()
    }

}

@Preview(showBackground = true)
@Composable
fun LazyListWithFloatingButtonPreview() {
    val enabledButton: Boolean = true
    val textButton: String = "Submit"
    val topPaddingButton: Dp = 32.dp
    val bottomPaddingButton: Dp = 32.dp
    LazyListWithFloatingButton(
        bottomContent = {
            Spacer(modifier = Modifier.size(topPaddingButton))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RegularButton(
                    modifier = Modifier,
                    enabled = enabledButton,
                    text = textButton,
                ) {
//                    on Submit Click
                }
            }
            Spacer(modifier = Modifier.size(bottomPaddingButton))
        },
    ) {
        items(20) {
            Text(
                text = "Please, scan Case card ID barcode",
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