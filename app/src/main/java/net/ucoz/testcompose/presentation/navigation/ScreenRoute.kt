package net.ucoz.testcompose.presentation.navigation

sealed class ScreenRoute(val route: String ) {
    object First: ScreenRoute("first_screen")
}