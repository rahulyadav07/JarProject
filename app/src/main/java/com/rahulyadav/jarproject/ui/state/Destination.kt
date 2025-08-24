package com.rahulyadav.jarproject.ui.state

sealed class Destinations(val route: String) {
    object Onboarding : Destinations("onboarding")
    object Landing : Destinations("landing")
}
