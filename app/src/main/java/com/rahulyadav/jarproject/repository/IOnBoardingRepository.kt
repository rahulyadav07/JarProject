package com.rahulyadav.jarproject.repository

import com.rahulyadav.jarproject.model.ManualBuyEducationData


interface IOnBoardingRepository {
        suspend fun getOnboardingEducationData(): Result<ManualBuyEducationData?>
}
