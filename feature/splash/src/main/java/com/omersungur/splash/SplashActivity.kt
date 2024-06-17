package com.omersungur.splash

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.omersungur.auth.AuthenticationActivity
import com.omersungur.compose_ui.theme.C_C8DDFF
import com.omersungur.domain.util.SharedPref
import com.omersungur.domain.util.goToTheActivity
import com.omersungur.onboarding.OnboardingActivity
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val scale = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                delay(500L)

                scale.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 2500, easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }))

                checkShowedOnboardingStateAndGoOtherActivity(activity = this@SplashActivity)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.C_C8DDFF)
                    .scale(scale.value),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_splash),
                    contentDescription = "Splash Screen with Image",
                    modifier = Modifier.size(250.dp),
                )
            }
        }
    }
}

private fun checkShowedOnboardingStateAndGoOtherActivity(activity: Activity) {
    if (SharedPref(context = activity).isShowingOnboardingScreen()) {
        activity.goToTheActivity(activityToGo = OnboardingActivity(), isFinish = true)
    } else if (SharedPref(context = activity).isLoggedIn()) {
        // TODO: Go to the main activity
    } else {
        activity.goToTheActivity(activityToGo = AuthenticationActivity(), isFinish = true)
    }
}
