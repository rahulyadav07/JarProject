
package com.rahulyadav.jarproject.ui.screens.onboarding

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulyadav.jarproject.model.EducationCard
import com.rahulyadav.jarproject.repository.IOnBoardingRepository
import com.rahulyadav.jarproject.repository.OnboardingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val repository: IOnBoardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Loading)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()


    private val _expandedIndex = MutableStateFlow(-1)
    val expandedIndex: StateFlow<Int> = _expandedIndex.asStateFlow()

    private val _visibleItems = MutableStateFlow(0)
    val visibleItems: StateFlow<Int> = _visibleItems.asStateFlow()

    private val _showCTA = MutableStateFlow(false)
    val showCTA: StateFlow<Boolean> = _showCTA.asStateFlow()

    private val _backgroundColors = MutableStateFlow(listOf(Color("#22034a".toColorInt()), Color("#22034a".toColorInt())))
    val backgroundColors: StateFlow<List<Color>> = _backgroundColors

    private val _previousExpandedIndex = MutableStateFlow(-1)
    val previousExpandedIndex: StateFlow<Int> = _previousExpandedIndex.asStateFlow()

    private val _showIntroText = MutableStateFlow(true)
    val showIntroText: StateFlow<Boolean> = _showIntroText.asStateFlow()

    private var animationStarted = false

    init {
        loadOnboardingData()
    }

    private fun loadOnboardingData() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Loading
            repository.getOnboardingEducationData()
                .onSuccess { educationData ->
                    _uiState.value = educationData?.let { OnboardingUiState.Success(it) }!!
                }
                .onFailure { exception ->
                    _uiState.value = OnboardingUiState.Error(
                        exception.message ?: "Something Went Wrong!"
                    )
                }
        }
    }

    fun startCardAnimationCycle(cards: List<EducationCard>, expandedStay: Long?) {
        if (animationStarted) return
        animationStarted = true

        viewModelScope.launch {
            _showIntroText.value = true
            delay(2000)
            _showIntroText.value = false
            _visibleItems.value = 0
            _expandedIndex.value = -1

            delay(300)
            _visibleItems.value = 1
            _expandedIndex.value = 0
            updateBackgroundColors(cards[0].startGradient, cards[0].endGradient)

            cards.drop(1).forEachIndexed { index, _ ->
                delay(expandedStay ?: 1000)
                updateBackgroundColors(cards[index+1].startGradient, cards[index+1].endGradient)
                _visibleItems.value = index + 2
                _expandedIndex.value = index + 1
            }
            delay(3000)
            _showCTA.value = true
        }
    }

    fun onExpandCard(index: Int) {
        if (_expandedIndex.value == index) {
            _previousExpandedIndex.value = _expandedIndex.value
            _expandedIndex.value = -1
        } else {
            _previousExpandedIndex.value = _expandedIndex.value
            _expandedIndex.value = index
        }
    }

    fun updateBackgroundColors(startColor: String? = null, endColor: String? = null) {
        try {
            _backgroundColors.value = listOf(Color(startColor?.toColorInt()?:0), Color(endColor?.toColorInt()?:0))
        } catch (e: Exception) {
            _backgroundColors.value = listOf(Color.Black, Color.White)
        }
    }
}
