package net.ucoz.testcompose.presentation.widget.lazyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.core.widget.NestedScrollView
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun LazyListWithFloatingButtonV5(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent:
    LazyListScope.() -> Unit,
) {
    val context = LocalContext.current



    val constraints = ConstraintSet {
        val lazyColumn = createRefFor("lazyColumn")
        val container = createRefFor("container")
        val verticalChain =
            createVerticalChain(lazyColumn, container, chainStyle = ChainStyle.SpreadInside)
        constrain(lazyColumn) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(container.top)
            height = Dimension.fillToConstraints
        }
        constrain(container) {
//            top.linkTo(lazyColumn.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.wrapContent
        }
    }


        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                userScrollEnabled = true,
                modifier = Modifier.layoutId("lazyColumn"),
                state = state,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                topContent()
            }
            Box(
                modifier = Modifier
                    .layoutId("container"),
            ) {
                bottomContent()
            }


        }

}

