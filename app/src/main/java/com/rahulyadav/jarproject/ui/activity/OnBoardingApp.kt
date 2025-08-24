package com.rahulyadav.jarproject.ui.activity


import OnboardingScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahulyadav.jarproject.network.NetworkModule
import com.rahulyadav.jarproject.repository.OnboardingUiState
import com.rahulyadav.jarproject.ui.activity.composeComponent.ReusableTopBar
import com.rahulyadav.jarproject.ui.state.Destinations


@Composable
fun NavigationBasedApp(viewModel: OnBoardingViewModel = viewModel {
    OnBoardingViewModel(NetworkModule.repository)
}) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    val gradientColors by viewModel.backgroundColors.collectAsState()

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            gradientColors[0],
            gradientColors[1].copy(alpha = 0.8f),
            gradientColors[1].copy(alpha = 0.3f)
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                when (val currentState = uiState) {
                    is OnboardingUiState.Error -> { }
                    is OnboardingUiState.Success -> {
                        if (currentState.educationData.shouldShowOnLandingPage) {
                            ReusableTopBar("LandingPage")
                        } else {
                            ReusableTopBar("OnBoardingPage")
                        }
                    }
                    OnboardingUiState.Loading -> { }
                }
            }
        )
        { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Destinations.Onboarding.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Destinations.Onboarding.route) {
                    OnboardingScreen(
                        educationData = (uiState as? OnboardingUiState.Success)?.educationData,
                        onNavigateToLanding = {
                            navController.navigate(Destinations.Landing.route) {
                                popUpTo(Destinations.Onboarding.route) { inclusive = true }
                            }
                        },
                        onBackgroundColorChange = {
                            viewModel.updateBackgroundColors(it)
                        }
                    )
                }
                composable(Destinations.Landing.route) {
//                LandingScreen()
                }
            }


            LaunchedEffect (uiState) {
                when (val currentState = uiState) {
                    is OnboardingUiState.Success -> {
                        if (currentState.educationData.shouldShowOnLandingPage) {
                            navController.navigate(Destinations.Landing.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Destinations.Onboarding.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    MaterialTheme {

    }
}