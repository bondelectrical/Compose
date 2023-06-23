package net.ucoz.testcompose.stopWatch

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.R
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StopWatch(viewModel: StopWatchViewModel) {
    StopWatchScreen(
        seconds = viewModel.seconds,
        minutes = viewModel.minutes,
        hours = viewModel.hours,
        isPlaying = viewModel.isPlaying,
        onStart = { viewModel.start() },
        onStop = { viewModel.stop() },
        onPause = { viewModel.pause() }
    )


}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StopWatchScreen(
    seconds: String,
    minutes: String,
    hours: String,
    isPlaying: Boolean,
    onStart: () -> Unit = {},
    onStop: () -> Unit = {},
    onPause: () -> Unit = {}
) {
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
                AnimatedContent(targetState = hours, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }
                Text(text = ":", fontSize = 24.sp)
                AnimatedContent(targetState = minutes, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }
                Text(text = ":", fontSize = 24.sp)
                AnimatedContent(targetState = seconds, transitionSpec = numberTransitionSpec) {
                    Text(text = it, fontSize = 24.sp)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.background(Color.LightGray, RoundedCornerShape(50))
            ) {
                AnimatedContent(targetState = isPlaying) {
                    if (it)
                        IconButton(onClick = onPause) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_pause_24),
                                contentDescription = ""
                            )
                        }
                    else
                        IconButton(onClick = onStart) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_play_arrow_24),
                                contentDescription = ""
                            )
                        }
                }
                IconButton(onClick = onStop) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_stop_24),
                        contentDescription = ""
                    )
                }

            }

        }

    }


}

@Preview(widthDp = 360, heightDp = 640, showBackground = true)
@Composable
fun StopWatchScreenPreview() {
    StopWatchScreen(
        "00",
        "00",
        "00",
        false,
    )
}