package com.omersungur.onboarding.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class OnboardingScreens {

    @Serializable
    data object FirstScreen : OnboardingScreens()

    @Serializable
    data object SecondScreen : OnboardingScreens()

    @Serializable
    data object ThirdScreen : OnboardingScreens()
}
