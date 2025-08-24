package com.rahulyadav.jarproject.model

data class  ManualBuyEducationData(
    val actionText: String,
    val bottomToCenterTranslationInterval: Int,
    val cohort: String,
    val collapseCardTiltInterval: Int,
    val collapseExpandIntroInterval: Int,
    val combination: Any,
    val ctaLottie: String,
    val educationCardList: List<EducationCard>,
    val expandCardStayInterval: Int,
    val introSubtitle: String,
    val introSubtitleIcon: String,
    val introTitle: String,
    val saveButtonCta: SaveButtonCta,
    val screenType: String,
    val seenCount: Any,
    val shouldShowBeforeNavigating: Boolean,
    val shouldShowOnLandingPage: Boolean,
    val toolBarIcon: String,
    val toolBarText: String
)