package com.example.xueapp.ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AccountScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    accountViewModel: AccountViewModel = viewModel()
) {
    val context = LocalContext.current
    // TODO: Replace with your actual token retrieval logic
    val token = "your_auth_token"

    LaunchedEffect(Unit) {
        accountViewModel.getMe(token)
    }

    val user = accountViewModel.user
    val logoutState = accountViewModel.logoutState

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    LaunchedEffect(logoutState) {
        when (logoutState) {
            is LogoutState.Success -> {
                navController.navigate("login") { popUpTo(0) }
            }
            is LogoutState.Error -> {
                alertMessage = logoutState.message
                showAlert = true
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )
            Text(
                text = "My Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Transparent,
                modifier = Modifier.size(28.dp)
            )
        }

        // Content
        if (user == null) {
            // No account UI
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No account found",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "You are not signed in yet.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text(
                        text = "Create or Sign In",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Logged-in UI
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Account Information",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.7f))

            // User info
            Text(
                text = "Email: ${user?.email ?: "Loading..."}",
                color = Color.White,
                fontSize = 16.sp
            )

                Spacer(modifier = Modifier.height(16.dp))

            // Sign Out
            Button(
                onClick = { accountViewModel.logout(token) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = logoutState !is LogoutState.Loading
            ) {
                if (logoutState is LogoutState.Loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {

                    Text(
                        text = "Sign Out",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Alert dialog
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text("OK", color = Color.Cyan)
                    }
                },
                title = { Text("Logout Failed", color = Color.White) },
                text = { Text(alertMessage, color = Color.White) },
                containerColor = Color.DarkGray
            )
        }
    }
}
