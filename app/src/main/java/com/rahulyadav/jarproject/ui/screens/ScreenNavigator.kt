// NavigationBasedApp.kt
package com.rahulyadav.jarproject.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rahulyadav.jarproject.network.NetworkModule
import com.rahulyadav.jarproject.repository.OnboardingUiState
import com.rahulyadav.jarproject.ui.screens.composeComponent.ReusableTopBar
import com.rahulyadav.jarproject.ui.screens.landing.LandingScreen

import com.rahulyadav.jarproject.ui.screens.onboarding.OnBoardingViewModel
import com.rahulyadav.jarproject.ui.screens.onboarding.OnboardingScreen

@Composable
fun ScreenNavigator(viewModel: OnBoardingViewModel = viewModel {
    OnBoardingViewModel(NetworkModule.repository)
}) {
    val uiState by viewModel.uiState.collectAsState()
    val gradientColors by viewModel.backgroundColors.collectAsState()


    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            gradientColors[0],
            gradientColors[1].copy(alpha = 1f),
            gradientColors[1].copy(alpha = 0.5f)
        )
    )

    var showLandingScreen by remember { mutableStateOf(false) }
    var onboardingVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                when (val currentState = uiState) {
                    is OnboardingUiState.Error -> { }
                    is OnboardingUiState.Success -> {
                        ReusableTopBar(
                            if (showLandingScreen) "LandingPage" else "OnBoardingPage"
                        )
                    }
                    OnboardingUiState.Loading -> { }
                }
            }
        ) { paddingValues ->

            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {


                AnimatedVisibility(
                    visible = onboardingVisible,
                    exit = slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 600)
                    ) + fadeOut(animationSpec = tween(600))
                ) {
                    OnboardingScreen(
                        educationData = (uiState as? OnboardingUiState.Success)?.educationData,
                        onNavigateToLanding = {

                            onboardingVisible = false
                            showLandingScreen = true
                            viewModel.updateBackgroundColors("#22034a","#22034a")
                        },
                        onBackgroundColorChange = {
                            viewModel.updateBackgroundColors(it.startGradient, it.endGradient)
                        },
                        viewModel = viewModel
                    )
                }

                // Landing screen
                AnimatedVisibility(
                    visible = showLandingScreen,
                    enter = fadeIn(animationSpec = tween(durationMillis = 600))
                ) {
                    LandingScreen()
                }
            }
        }
    }
}


