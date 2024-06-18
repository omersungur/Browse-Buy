package com.omersungur.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.omersungur.auth.navigation.AuthenticationNavGraph
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.util.goToTheActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            BrowseAndBuyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        AuthenticationNavGraph(navController = navController) {
                            goToTheActivity(activityToGo = it, isFinish = true)
                        }
                    }
                }
            }
        }
    }
}
