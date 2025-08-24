package com.rahulyadav.jarproject.ui.activity.composeComponent


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahulyadav.jarproject.R
import com.rahulyadav.jarproject.ui.state.LocalGradientState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableTopBar(
    title: String
) {
    val gradientState = LocalGradientState.current
    val currentGradient by gradientState.currentGradient
    val isGradientActive = currentGradient != null

    androidx.compose.material3.TopAppBar(
        title = {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {


                Image(
                    painter = painterResource(id = R.drawable.back_button),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (isGradientActive) {
                        androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                    } else null
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isGradientActive) Color.White else Color.Black
                )
            }
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent, // Make transparent to show gradient
            titleContentColor = if (isGradientActive) Color.White else Color.Black
        )
    )
}