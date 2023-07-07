package net.ucoz.testcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import net.ucoz.testcompose.presentation.firstScreen.FirstScreen
import net.ucoz.testcompose.presentation.firstScreen.FirstScreenViewModel
import net.ucoz.testcompose.presentation.jobList.JobListScreen
import net.ucoz.testcompose.presentation.jobList.JobListViewModel
import net.ucoz.testcompose.presentation.models.JobUI

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
    val viewModelJobList: JobListViewModel = hiltViewModel()
    JobListScreen(
        state = viewModelJobList.viewState.value,
        effectFlow = viewModelJobList.effect,
        onEventSent = { event -> viewModelJobList.setEvent(event) },
        onNavigationRequested = { navigationEffect ->

        }
    )
}