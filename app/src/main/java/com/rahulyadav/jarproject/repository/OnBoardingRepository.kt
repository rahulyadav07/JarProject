package com.rahulyadav.jarproject.repository

import com.rahulyadav.jarproject.model.ManualBuyEducationData
import com.rahulyadav.jarproject.network.OnboardingApiService


class OnBoardingRepository(
    private val apiService: OnboardingApiService
) {
    suspend fun getOnboardingEducationData(): Result<ManualBuyEducationData?> {
        return try {
            val response = apiService.getOnboardingData()
            if (response.isSuccessful && response.body() != null) {

                val educationData = response.body()?.data?.manualBuyEducationData
                Result.success(educationData)
            } else {

                Result.failure(Exception("Failed to load onboarding data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


sealed class OnboardingUiState {
    object Loading : OnboardingUiState()
    data class Success(val educationData: ManualBuyEducationData) : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}