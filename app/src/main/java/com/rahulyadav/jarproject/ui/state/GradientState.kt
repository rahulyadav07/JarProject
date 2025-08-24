package com.rahulyadav.jarproject.ui.state




import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class GradientState {
    private val _currentGradient = mutableStateOf<Brush?>(null)
    val currentGradient: State<Brush?> = _currentGradient

    fun updateGradient(startColor: String, endColor: String) {
        val start = Color(android.graphics.Color.parseColor(startColor))
        val end = Color(android.graphics.Color.parseColor(endColor))

        // Use radial glow instead of flat vertical
        _currentGradient.value = Brush.radialGradient(
            colors = listOf(
                start.copy(alpha = 0.95f),
                start.copy(alpha = 0.6f),
                start.copy(alpha = 0.3f),
                Color.Black.copy(alpha = 0.7f) // dark edges like your screenshot
            ),
            radius = 1200f // tweak per screen size
        )
    }


    fun clearGradient() {
        _currentGradient.value = null
    }
}


val LocalGradientState = compositionLocalOf { GradientState() }
