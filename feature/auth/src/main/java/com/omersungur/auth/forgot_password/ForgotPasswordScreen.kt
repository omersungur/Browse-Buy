package com.omersungur.auth.forgot_password

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omersungur.auth.R
import com.omersungur.compose_ui.component.text_field.BBOutlinedTextField
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_0E2DAC
import com.omersungur.compose_ui.theme.C_1443C3
import com.omersungur.compose_ui.theme.C_191D23
import com.omersungur.compose_ui.theme.C_6C6F72
import com.omersungur.compose_ui.theme.C_77707F
import com.omersungur.compose_ui.theme.Dimen

@Composable
fun ForgotPasswordScreenUI(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    with(uiState) {
        if (loadingState) {
            CircularProgressIndicator()
            return@with
        }

        if (isHaveError) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.updateErrorStateWithDefaultValue()
            return@with
        }

        if (isSuccess) {
            Toast.makeText(context, "Your password has been reset", Toast.LENGTH_LONG).show()
            viewModel.updateSuccessStateWithDefaultValue()
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.spacing_l),
    ) {

        Text(
            text = stringResource(R.string.forgot_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimen.spacing_xxl),
            style = TextStyle(
                fontSize = Dimen.font_size_22,
                fontWeight = FontWeight.SemiBold,
                color = Color.C_191D23,
            ),
        )

        Text(
            text = stringResource(R.string.enter_the_email_address_registered_with_your_account_we_ll_send_you_a_link_to_reset_your_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimen.spacing_xs),
            style = TextStyle(
                fontSize = Dimen.font_size_s1,
                color = Color.C_6C6F72,
            ),
        )

        Spacer(modifier = Modifier.height(Dimen.spacing_xl))

        BBOutlinedTextField(
            textFieldValue = stringResource(R.string.text_field_email_address),
            textFieldHint = stringResource(R.string.text_field_hint_gmail),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        ) {
            email = it
        }

        Spacer(modifier = Modifier.height(Dimen.spacing_l))

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.C_1443C3),
            onClick = {
                viewModel.resetPassword(email)
            },
        ) {
            Text(text = stringResource(R.string.submit))
        }

        Spacer(modifier = Modifier.height(Dimen.spacing_xl))

        Row {
            Text(
                text = stringResource(R.string.remembered_password),
                color = Color.C_77707F,
            )

            Spacer(modifier = Modifier.width(Dimen.spacing_xxs))

            Text(
                text = stringResource(R.string.login_to_your_account),
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                color = Color.C_0E2DAC,
            )
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenUIPreview() {
    BrowseAndBuyAppTheme {
        ForgotPasswordScreenUI(navController = rememberNavController())
    }
}