package com.omersungur.onboarding.screen_first

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omersungur.onboarding.R
import com.omersungur.onboarding.common.OnBoardingScreenUI
import com.omersungur.onboarding.navigation.OnboardingScreens

@Composable
fun FirstScreen(navController: NavController) {
    OnBoardingScreenUI(
        imageSize = 350.dp,
        imageTopPadding = 100.dp,
        image = R.drawable.first_screen_image,
        imageDot = R.drawable.first_screen_dot,
        contentDescriptionForImage = stringResource(R.string.two_cardboard_packs),
        contentDescriptionForDotImage = stringResource(R.string.three_dots_showing_the_first),
        title = stringResource(R.string.find_the_item_you_are_looking_for),
        description = stringResource(R.string.first_screen_description),
    ) {
        navController.navigate(OnboardingScreens.SecondScreen)
    }
}
