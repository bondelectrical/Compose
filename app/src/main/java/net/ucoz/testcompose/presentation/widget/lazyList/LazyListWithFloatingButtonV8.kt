package net.ucoz.testcompose.presentation.widget.lazyList

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import net.ucoz.testcompose.R
import net.ucoz.testcompose.presentation.thirdScreen.ThirdScreenContract
import net.ucoz.testcompose.presentation.thirdScreen.composables.DeleteAddButtons
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar
import net.ucoz.testcompose.presentation.widget.scroll.toDp
import net.ucoz.testcompose.ui.theme.DarkBlue

@Composable
fun LazyListWithFloatingButtonV8(
    bottomContent: @Composable () -> Unit,
    enabledIndicator: Boolean = false,
    state: LazyListState = rememberLazyListState(),
    thickness: Dp = 6.dp,
    topPaddingIndicator: Dp = 0.dp,
    bottomPaddingIndicator: Dp = 0.dp,
    endPaddingIndicator: Dp = 8.dp,
    topContent: LazyListScope.() -> Unit,

    ) {
    AndroidView(factory = { context->
        View.inflate(context, R.layout.nested_scroll_layout,null)
    }, update = { view ->

        // Here we bind a variable with findviewbyid to access compose

        val bottomComposeView: ComposeView = view.findViewById(R.id.btn_content)
        bottomComposeView.setContent {
            bottomContent()
//            LazyColumn(
//                userScrollEnabled = true,
//                modifier = if (enabledIndicator) {
//                    Modifier
//                        .drawVerticalScrollbar(
//                            state,
//                            thickness = thickness,
//                            topPaddingIndicator = topPaddingIndicator,
//                            bottomPaddingIndicator = bottomPaddingIndicator,
//                            endPaddingIndicator = endPaddingIndicator,
//                        )
//                } else {
//                    Modifier
//                },
//                state = state,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Top
//            ) {
//                topContent()
//            }
        }
        val topComposeView: ComposeView = view.findViewById(R.id.list_content)
        topComposeView.setContent {
//            LazyColumn {
//                // Add a single item
//                item {
//                    Text(text = "First item")
//                }
//
//                // Add 5 items
//                items(5) { index ->
//                    Text(text = "Item: $index")
//                }
//
//                // Add another single item
//                item {
//                    Text(text = "Last item")
//                }
//            }
//            bottomContent()
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
    })

    }

