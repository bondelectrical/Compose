package net.ucoz.testcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = ScreenRoute.First.route) {
        composable(ScreenRoute.First.route) {
            FirstScreenDestination(navHostController)
        }

    }

}