package com.omersungur.auth.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthenticationScreens {

    @Serializable
    data object SignInScreen : AuthenticationScreens()

    @Serializable
    data object SignUpScreen : AuthenticationScreens()

    @Serializable
    data object ForgotPasswordScreen : AuthenticationScreens()
}
