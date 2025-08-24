package com.rahulyadav.jarproject.ui.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulyadav.jarproject.model.EducationCard
import com.rahulyadav.jarproject.model.ManualBuyEducationData
import com.rahulyadav.jarproject.repository.OnBoardingRepository
import com.rahulyadav.jarproject.repository.OnboardingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt


class OnBoardingViewModel(
    private val repository: OnBoardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Loading)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _currentCardIndex = MutableStateFlow(0)
    val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

    private val _isCardExpanded = MutableStateFlow(false)
    val isCardExpanded: StateFlow<Boolean> = _isCardExpanded.asStateFlow()

    private val _onboardingShown = mutableStateOf(false)
    val onboardingShown: State<Boolean> = _onboardingShown

    init {
        loadOnboardingData()
    }

    private fun loadOnboardingData() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Loading
            repository.getOnboardingEducationData()
                .onSuccess { educationData ->
                    _uiState.value = educationData?.let { OnboardingUiState.Success(it) }!!
                    startCardAnimationCycle(educationData)
                }
                .onFailure { exception ->
                    _uiState.value = OnboardingUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    private fun startCardAnimationCycle(educationData: ManualBuyEducationData?) {
        viewModelScope.launch {
            val cardCount = educationData?.educationCardList?.size ?: 0

            for (index in 0 until cardCount) {


                _isCardExpanded.value = false
                educationData?.collapseCardTiltInterval?.toLong()?.let { delay(it) }

                // Expand card
                _isCardExpanded.value = true
                educationData?.expandCardStayInterval?.toLong()?.let { delay(it) }

                // Move to next card
                _currentCardIndex.value = (_currentCardIndex.value + 1) % cardCount!!
                educationData?.collapseExpandIntroInterval?.toLong()?.let { delay(it) }

            }
        }
    }

    fun onCardIndexChanged(index: Int) {
        _currentCardIndex.value = index
    }

    fun onRetry() {
        loadOnboardingData()
    }

    fun onCompleteOnboarding() {
        // Handle onboarding completion logic here
    }

    private val _backgroundColors = MutableStateFlow(
        listOf(Color(0xFF000000), Color(0xFFFFFFFF)) // default black → white
    )
    val backgroundColors: StateFlow<List<Color>> = _backgroundColors

    fun updateBackgroundColors(card: EducationCard) {
        try {
            val startColor = Color(card.startGradient.toColorInt())
            val endColor = Color(card.endGradient.toColorInt())
            _backgroundColors.value = listOf(startColor, endColor)
        } catch (e: Exception) {
            _backgroundColors.value = listOf(Color.Black, Color.White)
        }
    }



    fun onExpandCard(index: Int) {
        if (currentCardIndex.value == index) {
            _isCardExpanded.value = !_isCardExpanded.value
        } else {
            _isCardExpanded.value = true
            _currentCardIndex.value = index
        }
    }

}