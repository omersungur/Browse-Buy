package com.omersungur.auth.sign_in

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omersungur.auth.R
import com.omersungur.auth.navigation.AuthenticationScreens
import com.omersungur.compose_ui.component.button.BBGoogleAuthButton
import com.omersungur.compose_ui.component.text_field.BBOutlinedTextField
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_3347C4
import com.omersungur.compose_ui.theme.Dimen
import com.omersungur.domain.model.JWTUser

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val localFocusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val verticalScroll = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordShowing by remember { mutableStateOf(false) }
    var checkedJWTLogin by remember { mutableStateOf(false) }
    var checkedRememberMe by remember { mutableStateOf(false) }
    var visualTransformation: VisualTransformation by remember { mutableStateOf(PasswordVisualTransformation()) }

    with(uiState) {
        if (isLoading) {
            CircularProgressIndicator()
            return@with
        }

        if (isSuccessEmailAndPasswordLogin) {
            Toast.makeText(LocalContext.current, "Login Success", Toast.LENGTH_SHORT).show()
        }

        if (isSuccessJWTLogin) {
            Toast.makeText(LocalContext.current, "Success!", Toast.LENGTH_SHORT).show()
            jwtData?.let {
                println(it.firstName)
            }
            viewModel.updateErrorStatesWithDefaultValues()
        }

        if (isHaveError) {
            Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.updateErrorStatesWithDefaultValues()
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
                contentDescription = stringResource(R.string.turkcell_logo),
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_xl))

            Text(
                text = stringResource(R.string.browse_buy),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = Dimen.font_size_l1,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_l))

            BBOutlinedTextField(
                textFieldValue = if (!checkedJWTLogin) stringResource(R.string.email_address) else "Username",
                textFieldHint = if (!checkedJWTLogin) stringResource(R.string.example_gmail_com) else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                ),
            ) {
                email = it
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            BBOutlinedTextField(
                textFieldValue = stringResource(R.string.password),
                textFieldHint = stringResource(R.string.password_hint),
                trailingIconContent = {
                    if (!isPasswordShowing) {
                        Icon(
                            modifier = Modifier.clickable {
                                isPasswordShowing = !isPasswordShowing
                                visualTransformation = VisualTransformation.None

                            },
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(R.string.password_is_not_visible),
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable {
                                isPasswordShowing = !isPasswordShowing
                                visualTransformation = PasswordVisualTransformation()
                            },
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(R.string.password_is_visible),
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                visualTransformation = visualTransformation,
                rowContent = {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        modifier = Modifier.clickable {
                            navController.navigate(AuthenticationScreens.ForgotPasswordScreen)
                        },
                        color = Color.C_3347C4,
                    )
                },
            ) {
                password = it
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checkedJWTLogin, onCheckedChange = { checkedJWTLogin = !checkedJWTLogin })

                    Text(text = stringResource(R.string.login_with_jwt))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checkedRememberMe, onCheckedChange = { checkedRememberMe = !checkedRememberMe })

                    Text(text = stringResource(R.string.remember_me))
                }
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.C_3347C4),
                onClick = {
                    if (checkedJWTLogin) {
                        val jwtUser = JWTUser(email, password)
                        viewModel.loginWithJWT(jwtUser)
                    } else {
                        viewModel.loginWithEmailAndPassword(email, password)
                    }
                },
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = stringResource(R.string.don_t_you_have_an_account))

                Spacer(modifier = Modifier.width(Dimen.spacing_xxs))

                Text(
                    text = stringResource(R.string.sign_up_here),
                    modifier = Modifier.clickable {
                        navController.navigate(AuthenticationScreens.SignUpScreen)
                    },
                    color = Color.C_3347C4,
                )
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m2))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.or),
                    modifier = Modifier.padding(horizontal = Dimen.spacing_m1),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                )

                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_m1))

            BBGoogleAuthButton(
                modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                title = stringResource(R.string.sign_in_with_google),
                contentDescription = stringResource(R.string.google_logo),
            ) {
                // TODO: Login with Google
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    BrowseAndBuyAppTheme {
        SignInScreen(navController = rememberNavController())
    }
}