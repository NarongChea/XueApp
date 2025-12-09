package com.example.xueapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.xueapp.data.TokenManager

@Composable
fun LandingScreen(navController: NavController) {
    val context = LocalContext.current
    val tokenManager = TokenManager(context)

    // This effect will run once, and decide where to go.
    LaunchedEffect(Unit) {
        val destination = if (tokenManager.getAccessToken() != null) {
            "home"
        } else {
            "splash"
        }

        // Navigate to the correct destination and clear the back stack
        navController.navigate(destination) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }

    // This screen is intentionally blank while it redirects.
}
