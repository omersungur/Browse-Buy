package com.omersungur.auth.sign_up

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omersungur.auth.R
import com.omersungur.compose_ui.component.button.BBGoogleAuthButton
import com.omersungur.compose_ui.component.text_field.BBOutlinedTextField
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_3347C4
import com.omersungur.compose_ui.theme.Dimen

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    goToTheActivity: (activity: Activity) -> Unit,
) {
    val verticalScroll = rememberScrollState()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }

    with(uiState) {
        if (isLoading) {
            CircularProgressIndicator()
            return@with
        }

        if (isHaveError) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.updateStatesWithDefaultValues()
        }

        if (isSuccessSignUpWithEmailAndPassword) {
            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
            viewModel.updateStatesWithDefaultValues()
            // goToTheActivity(HomeActivity())
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = Dimen.spacing_m1)
                .verticalScroll(verticalScroll),
        ) {
            Spacer(modifier = Modifier.padding(top = 75.dp))

            Image(
                modifier = Modifier.padding(horizontal = Dimen.spacing_xxxl),
                painter = painterResource(id = R.drawable.turkcell_logo),
                contentDescription = stringResource(id = R.string.turkcell_logo),
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_xl))

            Text(
                text = stringResource(R.string.browse_buy),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = Dimen.font_size_l1,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            BBGoogleAuthButton(
                title = stringResource(R.string.sign_up_with_google),
                contentDescription = stringResource(id = R.string.google_logo),
            ) {
                // sonar - comment
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.or_sign_up_with),
                    modifier = Modifier.padding(horizontal = Dimen.spacing_m1),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                )

                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_l))

            BBOutlinedTextField(
                textFieldValue = stringResource(id = R.string.email_address),
                textFieldHint = stringResource(id = R.string.example_gmail_com),
            ) {
                email = it
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            BBOutlinedTextField(
                textFieldValue = stringResource(id = R.string.password),
                textFieldHint = null,
                trailingIconContent = {

                },
            ) {
                password = it
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_xs))

            BBOutlinedTextField(
                textFieldValue = stringResource(R.string.confirm_password),
                textFieldHint = null,
                trailingIconContent = {

                },
            ) {
                rePassword = it
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.C_3347C4),
                onClick = {
                    viewModel.signUpWithEmailAndPassword(email, password)
                },
            ) {
                Text(text = stringResource(R.string.sign_up))
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = stringResource(R.string.already_have_an_account))

                Spacer(modifier = Modifier.width(Dimen.spacing_xxs))

                Text(
                    text = stringResource(id = R.string.login),
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    color = Color.C_3347C4,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    BrowseAndBuyAppTheme {
        SignUpScreen(navController = rememberNavController()) {
            // sonar - comment
        }
    }
}