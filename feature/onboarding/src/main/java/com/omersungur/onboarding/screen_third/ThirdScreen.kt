package com.omersungur.onboarding.screen_third

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omersungur.onboarding.R
import com.omersungur.onboarding.common.OnBoardingScreenUI

@Composable
fun ThirdScreen(onNextClickForAuthActivityIntent: () -> Unit) {
    OnBoardingScreenUI(
        image = R.drawable.third_screen_image,
        imageDot = R.drawable.third_screen_dot,
        imageSize = 350.dp,
        imageTopPadding = 100.dp,
        title = stringResource(R.string.secure_payment),
        description = stringResource(R.string.third_screen_description),
        contentDescriptionForImage = stringResource(R.string.credit_card_image),
        contentDescriptionForDotImage = stringResource(R.string.three_dots_showing_the_third),
        onNextClicked = onNextClickForAuthActivityIntent,
    )
}