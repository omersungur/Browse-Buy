package com.omersungur.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.omersungur.auth.AuthenticationActivity
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.util.SharedPref
import com.omersungur.domain.util.goToTheActivity
import com.omersungur.onboarding.navigation.OnboardingNavGraph

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            BrowseAndBuyAppTheme {
                Scaffold { paddingValues ->
                    Surface(modifier = Modifier.padding(paddingValues)) {
                        OnboardingNavGraph(navController = navController) {
                            intentToAuth()
                        }
                    }
                }
            }
        }
    }

    private fun intentToAuth() {
        SharedPref(this).saveOnboardingShowingState()
        goToTheActivity(AuthenticationActivity(), true)
    }
}
