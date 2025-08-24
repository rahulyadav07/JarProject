package com.rahulyadav.jarproject.network

import com.rahulyadav.jarproject.model.OnBoardingCard
import retrofit2.Response
import retrofit2.http.GET

interface OnboardingApiService {
    @GET("_assets/shared/education-metadata.json")
    suspend fun getOnboardingData(): Response<OnBoardingCard>
}