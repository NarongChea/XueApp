package com.example.xueapp.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.xueapp.R
import com.example.xueapp.viewmodel.HomeViewModel
import com.example.xueapp.viewmodel.HomeViewModelFactory
import com.example.xueapp.viewmodel.UserState

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(context))
    val userState by homeViewModel.userState.collectAsState()

    LaunchedEffect(userState) {
        when (userState) {
            is UserState.Success -> {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            is UserState.Error -> {
                navController.navigate("welcome") {
                    popUpTo("splash") { inclusive = true }
                }
            }
            UserState.Loading -> {
                // Keep showing splash screen
            }
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE71616),
            Color(0xFFEC3232),
            Color(0xFFE35959)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo box
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Xue App",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Empowering Your Learning Journey",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
        }
    }
}
