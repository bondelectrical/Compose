package net.ucoz.testcompose.presentation.jobList.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ucoz.testcompose.R
import net.ucoz.testcompose.presentation.jobList.JobListContract
import net.ucoz.testcompose.presentation.models.JobUI
import net.ucoz.testcompose.presentation.widget.button.RegularButton
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButton
import net.ucoz.testcompose.ui.theme.DarkBlue

@Composable
fun JobList(list: List<JobUI>, onEventSent: (event: JobListContract.Event) -> Unit) {
    LazyListWithFloatingButton(
        bottomContent = {
            Spacer(modifier = androidx.compose.ui.Modifier.size(32.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RegularButton(
                    modifier = Modifier,
                    enabled = false,
                    text = stringResource(id = R.string.start_new_job),
                ) {
//                    on Submit Click
                }
            }
            Spacer(modifier = androidx.compose.ui.Modifier.size(32.dp))
        },
    ) {
        items(count = list.size) { index ->
            ItemJobList(item = list[index])

        }
    }

}

@Preview(showBackground = true)
@Composable
fun JobListPreview() {
    val item = JobUI(
        customerName = "Kyler Anderson",
        activity = "Enclosing",
        grid = "211305-155307X-XWZP",
        fileName = "SRA3_LIVE-PO P 013 2258_501445",
        totalTime = "00:00:12",
        isPause = true,
        isCompleted = false,
        pauseName = "Checking another job",
    )
    JobList(
        list = listOf<JobUI>(
            item.copy(),
            item.copy(
                isPause = false,
                isCompleted = false,
            ), item.copy(
                isPause = false,
                isCompleted = true,
            )
        ),
        onEventSent = {}
    )
}