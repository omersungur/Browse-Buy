package com.omersungur.auth.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omersungur.auth.forgot_password.ForgotPasswordScreenUI
import com.omersungur.auth.sign_in.SignInScreen
import com.omersungur.auth.sign_up.SignUpScreen

@Composable
fun AuthenticationNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthenticationScreens.SignInScreen,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
    ) {
        composable<AuthenticationScreens.SignInScreen> {
            SignInScreen(navController = navController)
        }

        composable<AuthenticationScreens.SignUpScreen> {
            SignUpScreen(navController = navController)
        }

        composable<AuthenticationScreens.ForgotPasswordScreen> {
            ForgotPasswordScreenUI(navController = navController)
        }
    }
}