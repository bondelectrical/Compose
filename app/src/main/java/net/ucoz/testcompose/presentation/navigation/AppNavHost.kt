package net.ucoz.testcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = ScreenRoute.Third.route) {
        composable(ScreenRoute.First.route) {
            FirstScreenDestination(navHostController)
        }
        composable(ScreenRoute.JobList.route) {
            JobListScreenDestination(navHostController)
        }
        composable(ScreenRoute.Second.route) {
            SecondScreenDestination(navHostController)
        }
        composable(ScreenRoute.Third.route) {
            ThirdScreenDestination(navHostController)
        }

    }

}