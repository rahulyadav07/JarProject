package com.rahulyadav.jarproject.ui.screens.composeComponent



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rahulyadav.jarproject.ui.theme.commonTextStyle

@Composable
fun RoundedCTA(
    text: String?,
    isExpanded: Boolean = false,
    backgroundColor: Color = Color("#272239".toColorInt()),
    contentColor: Color = Color.White,
    lottieUrl: String? = null,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            text = text?: "Know more",
            color = contentColor,
            fontSize = 14.sp,
            style = commonTextStyle
        )
        Spacer(modifier = Modifier.width(8.dp))

        if (lottieUrl != null) {

            val composition by rememberLottieComposition(LottieCompositionSpec.Url(lottieUrl))
            LottieAnimation(
                composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(24.dp)
            )
        } else {

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = contentColor
            )
        }
    }
}
