package com.omersungur.compose_ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.omersungur.compose_ui.R
import com.omersungur.compose_ui.theme.C_F4F7FF
import com.omersungur.compose_ui.theme.Dimen

@Composable
fun BBGoogleAuthButton(
    modifier: Modifier = Modifier,
    title: String,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.C_F4F7FF),
        shape = MaterialTheme.shapes.small,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google),
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(end = Dimen.spacing_xs)
                .size(Dimen.spacing_l),
        )

        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun DGoogleLoginButtonPreview() {
    BBGoogleAuthButton(
        title = "Sign in with Google",
        contentDescription = "Sign in with Google",
    ) { /* sonar-comment */ }
}