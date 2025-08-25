package com.rahulyadav.jarproject.ui.screens.composeComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.rahulyadav.jarproject.model.SaveButtonCta
import com.rahulyadav.jarproject.ui.theme.commonTextStyle



@Composable
fun RoundedCTA(
    lottieUrl:String?,
    ctaData:SaveButtonCta?,
    onClick: () -> Unit
) {
    val backgroundColor = Color(ctaData?.backgroundColor?.toColorInt()?:0)
    val contentColor = Color(ctaData?.textColor?.toColorInt()?: 0)
    val borderColor = ctaData?.strokeColor?.let { Color(it.toColorInt()?:0) }

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
            .then(
                if (borderColor != null) Modifier.border(1.dp, borderColor, RoundedCornerShape(30.dp))
                else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ctaData?.text ?: "",
            color = contentColor,
            fontSize = 14.sp,
            style = commonTextStyle
        )

        Spacer(modifier = Modifier.width(8.dp))

        val composition by rememberLottieComposition(LottieCompositionSpec.Url(lottieUrl ?: ""))
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(24.dp)
        )
    }
}
