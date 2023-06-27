package net.ucoz.testcompose.presentation.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import net.ucoz.testcompose.presentation.firstScreen.FirstScreen
import net.ucoz.testcompose.presentation.firstScreen.FirstScreenViewModel
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
