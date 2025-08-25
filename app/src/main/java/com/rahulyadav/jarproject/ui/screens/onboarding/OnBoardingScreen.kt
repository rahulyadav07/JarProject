package com.rahulyadav.jarproject.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahulyadav.jarproject.model.EducationCard
import com.rahulyadav.jarproject.model.ManualBuyEducationData
import com.rahulyadav.jarproject.ui.screens.composeComponent.EducationCard
import com.rahulyadav.jarproject.ui.screens.composeComponent.RoundedCTA



@Composable
fun OnboardingScreen(
    viewModel: OnBoardingViewModel,
    educationData: ManualBuyEducationData?,
    onNavigateToLanding: () -> Unit,
    onBackgroundColorChange: (EducationCard) -> Unit
) {
    val previousExpandedIndex by viewModel.previousExpandedIndex.collectAsState()
    val expandedIndex by viewModel.expandedIndex.collectAsState()

    val visibleItems by viewModel.visibleItems.collectAsState()
    val showCTA by viewModel.showCTA.collectAsState()
    val showIntroText by viewModel.showIntroText.collectAsState()

    val cardList = educationData?.educationCardList ?: emptyList()

    // Start animation cycle once cards are loaded
    LaunchedEffect(cardList) {
        if (cardList.isNotEmpty()) {
            viewModel.startCardAnimationCycle(cardList, educationData?.expandCardStayInterval?.toLong())
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        AnimatedVisibility(
            visible = showIntroText,

            exit = fadeOut(tween(500))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Welcome to Onboarding",
                    color = Color.White,
                    fontSize = 28.sp,
                    style = com.rahulyadav.jarproject.ui.theme.commonTextStyle
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cardList) { index, card ->
                val expandFrom = when {
                    previousExpandedIndex == -1 -> Alignment.Top
                    index > previousExpandedIndex -> Alignment.Bottom
                    index < previousExpandedIndex -> Alignment.Top
                    else -> Alignment.Top
                }

                AnimatedVisibility(
                    visible = index < visibleItems,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> 2 * fullHeight },
                        animationSpec = tween(
                            durationMillis = educationData?.bottomToCenterTranslationInterval
                                ?: 1000
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = educationData?.bottomToCenterTranslationInterval
                                ?: 1000
                        )
                    )
                ) {
                    EducationCard(
                        card = card,
                        isExpanded = expandedIndex == index,
                        expandFrom = expandFrom,
                        collapseExpandIntroInterval = educationData?.collapseExpandIntroInterval
                            ?: 500,
                        bottomToCenterTranslationInterval = educationData?.bottomToCenterTranslationInterval
                            ?: 1500,
                        firstTimeAnimation = false,
                        index = index,
                        onCardClick = {
                            viewModel.onExpandCard(index)
                            onBackgroundColorChange(card)
                        }
                    )
                }
            }

        }

        // CTA at bottom
        AnimatedVisibility(
            visible = showCTA,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(durationMillis = 800)
            ) + fadeIn(animationSpec = tween(durationMillis = 800)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            RoundedCTA(
                text = educationData?.actionText,
                lottieUrl = educationData?.ctaLottie,
                onClick = onNavigateToLanding
            )
        }
    }
}
