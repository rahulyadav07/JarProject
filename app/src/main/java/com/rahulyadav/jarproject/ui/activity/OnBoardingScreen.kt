import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rahulyadav.jarproject.model.EducationCard
import com.rahulyadav.jarproject.model.ManualBuyEducationData
import com.rahulyadav.jarproject.ui.activity.composeComponent.EducationCard
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    educationData: ManualBuyEducationData? = null,
    onNavigateToLanding: () -> Unit,
    onBackgroundColorChange: (EducationCard) -> Unit
) {
    var tiltedIndex by remember { mutableStateOf(-1) }
    var expandedIndex by remember { mutableStateOf(-1) }
    var previousExpandedIndex by remember { mutableStateOf(-1) }
    var firstTimeAnimation by remember { mutableStateOf(true) }

    val cardList = educationData?.educationCardList ?: emptyList()
    var hasAutoAnimationRun by remember { mutableStateOf(false) }
    var visibleItems by remember { mutableStateOf(0) }

    LaunchedEffect(cardList) {
        if (cardList.isNotEmpty() && !hasAutoAnimationRun) {
            hasAutoAnimationRun = true

            for ((index, card) in cardList.withIndex()) {

                // Step 1: Expand current card (straight)
                previousExpandedIndex = expandedIndex
                expandedIndex = index
                tiltedIndex = -1

                onBackgroundColorChange(card)

                visibleItems = index + 1
                if (index == 0) {
                    delay(educationData?.bottomToCenterTranslationInterval?.toLong() ?: 1500)
                }
                // Expanded stay time
                delay(educationData?.expandCardStayInterval?.toLong() ?: 3000)

                tiltedIndex = index

                delay(educationData?.collapseCardTiltInterval?.toLong() ?: 1000)

                delay(educationData?.collapseExpandIntroInterval?.toLong() ?: 500)
            }

            firstTimeAnimation = false

            tiltedIndex = -1

        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                                ?: 1000 // same duration as slide
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
                        firstTimeAnimation = firstTimeAnimation,
                        index = index,
                        isTilted = tiltedIndex == index,
                        visibleItems = visibleItems,
                        onCardClick = {
                            if (expandedIndex != index) {
                                previousExpandedIndex = expandedIndex
                                expandedIndex = index
                            }
                            onBackgroundColorChange(card)
                        }

                    )
                }
            }
        }
    }
}