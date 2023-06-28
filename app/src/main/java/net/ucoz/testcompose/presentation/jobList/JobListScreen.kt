package net.ucoz.testcompose.presentation.jobList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import net.ucoz.testcompose.presentation.jobList.composables.JobList

@Composable
fun JobListScreen(
    state: JobListContract.State,
    effectFlow: Flow<JobListContract.Effect>?,
    onEventSent: (event: JobListContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: JobListContract.Effect) -> Unit
) {

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->

        }?.collect()
    }
    JobList(state.jobList, onEventSent)
}