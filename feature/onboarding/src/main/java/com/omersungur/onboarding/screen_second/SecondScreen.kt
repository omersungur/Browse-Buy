package com.omersungur.onboarding.screen_second

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omersungur.onboarding.R
import com.omersungur.onboarding.common.OnBoardingScreenUI
import com.omersungur.onboarding.navigation.OnboardingScreens

@Composable
fun SecondScreen(navController: NavController) {
    OnBoardingScreenUI(
        imageSize = 350.dp,
        imageTopPadding = 100.dp,
        image = R.drawable.second_screen_image,
        imageDot = R.drawable.second_screen_dot,
        contentDescriptionForImage = stringResource(R.string.truck),
        contentDescriptionForDotImage = stringResource(R.string.three_dots_showing_the_second),
        title = stringResource(R.string.fast_delivery),
        description = stringResource(R.string.second_screen_description),
    ) {
        navController.navigate(OnboardingScreens.ThirdScreen)
    }
}
