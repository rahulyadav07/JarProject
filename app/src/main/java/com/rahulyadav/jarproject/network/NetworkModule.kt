package com.rahulyadav.jarproject.network

import com.rahulyadav.jarproject.repository.OnBoardingRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.rahulyadav.jarproject.network.OnboardingApiService

object NetworkModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://myjar.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




    val apiService: OnboardingApiService by lazy {
        retrofit.create(OnboardingApiService::class.java)
    }

    val repository: OnBoardingRepository by lazy {
        OnBoardingRepository(apiService)
    }
}
