package net.ucoz.testcompose.presentation.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import net.ucoz.testcompose.presentation.firstScreen.FirstScreen
import net.ucoz.testcompose.presentation.firstScreen.FirstScreenViewModel
import net.ucoz.testcompose.presentation.jobList.JobListScreen
import net.ucoz.testcompose.presentation.jobList.JobListViewModel
import net.ucoz.testcompose.presentation.jobList.JobUI
import net.ucoz.testcompose.stopWatch.StopWatchViewModel

@Composable
fun FirstScreenDestination(navController: NavHostController) {
//    val viewModel: FirstScreenViewModel by viewModels()
    val viewModel: FirstScreenViewModel = viewModel()
    FirstScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->

        }
    )
}

@Composable
fun JobListScreenDestination(navController: NavHostController) {
    val viewModelJobList: JobListViewModel = viewModel()
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
    JobListScreen(
        state = viewModelJobList.viewState.value.copy(
            jobList = listOf<JobUI>(
                item.copy(),
                item.copy(
                    isPause = false,
                    isCompleted = false,
                ), item.copy(
                    isPause = false,
                    isCompleted = true,
                )
            )
        ),
        effectFlow = viewModelJobList.effect,
        onEventSent = { event -> viewModelJobList.setEvent(event) },
        onNavigationRequested = { navigationEffect ->

        }
    )
}