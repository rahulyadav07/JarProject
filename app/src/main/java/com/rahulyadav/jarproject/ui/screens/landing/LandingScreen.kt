package com.rahulyadav.jarproject.ui.screens.landing


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.rahulyadav.jarproject.ui.theme.commonTextStyle

@Composable
fun LandingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#22034a".toColorInt())),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to Landing Screen",
            color = Color.White,
            style = commonTextStyle,
            fontSize = 22.sp
        )
    }
}
