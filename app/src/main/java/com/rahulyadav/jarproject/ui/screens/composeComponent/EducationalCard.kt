package com.rahulyadav.jarproject.ui.screens.composeComponent


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.rahulyadav.jarproject.model.EducationCard
import com.rahulyadav.jarproject.ui.theme.commonTextStyle

@Composable
fun EducationCard(
    card: EducationCard,
    isExpanded: Boolean,
    expandFrom: Alignment.Vertical,
    collapseExpandIntroInterval: Int,
    bottomToCenterTranslationInterval: Int,
    index: Int,
    firstTimeAnimation: Boolean,
    onCardClick: () -> Unit
) {

    val animationSpec: FiniteAnimationSpec<Float> =
        tween(durationMillis = collapseExpandIntroInterval)

    val animationSpec2 = tween<IntSize>(
        durationMillis = bottomToCenterTranslationInterval
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { if (!isExpanded) onCardClick() }
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(card.strokeStartColor.toColorInt()),
                        Color(card.strokeEndColor.toColorInt())
                    )
                ),
                shape = RoundedCornerShape(if (isExpanded) 16.dp else 50.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(card.backGroundColor))
        ),
        shape = RoundedCornerShape(if (isExpanded) 16.dp else 50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 12.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible = !isExpanded,
                enter = if (firstTimeAnimation)
                    fadeIn(animationSpec) + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(bottomToCenterTranslationInterval)
                    )
                else
                    fadeIn(animationSpec) + expandVertically(animationSpec = animationSpec2),
                exit = fadeOut(animationSpec) + shrinkVertically(animationSpec = animationSpec2)
            ) {
                CardHeader(card, isExpanded)
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = animationSpec2,
                    expandFrom = expandFrom
                ) + fadeIn(animationSpec),
                exit = shrinkVertically(
                    animationSpec = animationSpec2,
                    shrinkTowards = expandFrom
                ) + fadeOut(animationSpec)
            ) {
                ExpandableContent(card)
            }
        }
    }
}


@Composable
private fun CardHeader(
    card: EducationCard,
    isExpanded: Boolean,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = card.image,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = card.collapsedStateText,
            color = Color.White,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            style = commonTextStyle
        )

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ExpandableContent(
    card: EducationCard,
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = card.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = card.expandStateText,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            style = commonTextStyle
        )
    }

}
