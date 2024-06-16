package com.omersungur.onboarding.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.omersungur.onboarding.screen_first.FirstScreen
import com.omersungur.onboarding.screen_second.SecondScreen
import com.omersungur.onboarding.screen_third.ThirdScreen

@Composable
fun OnboardingNavGraph(
    navController: NavHostController,
    onNextClickForAuthActivityIntent: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = OnboardingScreens.FirstScreen,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
    ) {
        composable<OnboardingScreens.FirstScreen> {
            FirstScreen(navController)
        }

        composable<OnboardingScreens.SecondScreen> {
            SecondScreen(navController = navController)
        }

        composable<OnboardingScreens.ThirdScreen> {
            ThirdScreen(onNextClickForAuthActivityIntent = onNextClickForAuthActivityIntent)
        }
    }
}
