package com.omersungur.onboarding.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.omersungur.compose_ui.theme.C_3347C4
import com.omersungur.compose_ui.theme.Dimen
import com.omersungur.onboarding.R

@Composable
fun OnBoardingScreenUI(
    modifier: Modifier = Modifier,
    image: Int,
    imageDot: Int,
    imageSize: Dp,
    title: String,
    description: String,
    imageTopPadding: Dp,
    contentDescriptionForImage: String,
    contentDescriptionForDotImage: String,
    onNextClicked: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.weight(weight = 1f))

        Image(
            modifier = Modifier
                .size(imageSize)
                .padding(top = imageTopPadding)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = image),
            contentDescription = contentDescriptionForImage,
        )

        Spacer(modifier = Modifier.height(Dimen.spacing_xl))

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_xxl),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = Dimen.font_size_l,
                fontWeight = FontWeight.ExtraBold,
                color = Color.C_3347C4
            ),
        )

        Spacer(modifier = Modifier.height(height = Dimen.spacing_m1))

        Text(
            text = description,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = Dimen.spacing_xl),
            textAlign = TextAlign.Center,
            color = Color.Black.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.weight(weight = 1f))

        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = imageDot),
            contentDescription = contentDescriptionForDotImage,
        )

        Spacer(modifier = Modifier.height(height = Dimen.spacing_m1))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_xxl),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.C_3347C4,
            ),
            onClick = onNextClicked,
        ) {
            Text(
                text = stringResource(R.string.next),
                fontFamily = FontName,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

val FontName = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Light),
    Font(R.font.poppins_medium, FontWeight.SemiBold),
    Font(R.font.poppins_medium, FontWeight.ExtraBold),
)
