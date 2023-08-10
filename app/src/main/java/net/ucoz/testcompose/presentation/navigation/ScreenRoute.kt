package net.ucoz.testcompose.presentation.navigation

sealed class ScreenRoute(val route: String ) {
    object First: ScreenRoute("first_screen")
    object Second: ScreenRoute("second_screen")
    object JobList: ScreenRoute("job_list_screen")
    object Third: ScreenRoute("third_screen")
}