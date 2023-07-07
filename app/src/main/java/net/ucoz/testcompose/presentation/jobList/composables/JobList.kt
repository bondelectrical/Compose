package net.ucoz.testcompose.presentation.jobList.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.ucoz.testcompose.R
import net.ucoz.testcompose.presentation.jobList.JobListContract
import net.ucoz.testcompose.presentation.models.JobUI
import net.ucoz.testcompose.presentation.widget.lazyList.LazyListWithFloatingButton

@Composable
fun JobList(list: List<JobUI>, onEventSent: (event: JobListContract.Event) -> Unit) {
    LazyListWithFloatingButton(
        onButtonClick = {},
        textButton = stringResource(id = R.string.start_new_job),
        enabledIndicator = false,
        enabledButton = false
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