package net.ucoz.testcompose.presentation.firstScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import net.ucoz.testcompose.R
import net.ucoz.testcompose.presentation.widget.scroll.drawVerticalScrollbar

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FirstScreen(
    state: FirstScreenContract.State,
    effectFlow: Flow<FirstScreenContract.Effect>?,
    onEventSent: (event: FirstScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: FirstScreenContract.Effect) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->

        }?.collect()
    }
    Scaffold() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                val numberTransitionSpec: AnimatedContentScope<String>.() -> ContentTransform = {
                    slideInVertically(
                        initialOffsetY = { it }
                    ) + fadeIn() with slideOutVertically(
                        targetOffsetY = { -it }
                    ) + fadeOut() using SizeTransform(
                        false
                    )
                }
                AnimatedContent(targetState = state.hours, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }
                Text(text = ":", fontSize = 24.sp)
                AnimatedContent(targetState = state.minutes, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }
                Text(text = ":", fontSize = 24.sp)
                AnimatedContent(targetState = state.seconds, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.background(Color.LightGray, RoundedCornerShape(50))
            ) {
                AnimatedContent(targetState = state.isPlaying) {
                    if (it)
                        IconButton(onClick = {onEventSent(FirstScreenContract.Event.pauseClicked)}) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_pause_24),
                                contentDescription = ""
                            )
                        }
                    else
                        IconButton(onClick = {onEventSent(FirstScreenContract.Event.playClicked)}) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_play_arrow_24),
                                contentDescription = ""
                            )
                        }
                }
                IconButton(onClick = {onEventSent(FirstScreenContract.Event.stopClicked)}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_stop_24),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {onEventSent(FirstScreenContract.Event.stopTimeIntervalClicked)}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_timer_24),
                        contentDescription = ""
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .drawVerticalScrollbar(lazyListState),
                state = lazyListState
            ) {
                items(count = state.timesStop.size){index ->
                    Row(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        Text(
                            text = state.timesStop[index].time,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = state.timesStop[index].timeTotal,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )

                    }

                }


            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    FirstScreen(
        state = FirstScreenContract.State(),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}