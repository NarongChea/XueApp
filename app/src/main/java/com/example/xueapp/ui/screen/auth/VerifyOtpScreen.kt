package com.example.xueapp.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.xueapp.viewmodel.VerifyOtpState
import com.example.xueapp.viewmodel.VerifyOtpViewModel
import com.example.xueapp.viewmodel.VerifyOtpViewModelFactory

@Composable
fun VerifyOtpScreen(navController: NavController, email: String) {
    val context = LocalContext.current
    val verifyOtpViewModel: VerifyOtpViewModel = viewModel(factory = VerifyOtpViewModelFactory(context))

    var code by remember { mutableStateOf("") }
    val verifyOtpState by verifyOtpViewModel.verifyOtpState.collectAsState()

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    LaunchedEffect(verifyOtpState) {
        when (val state = verifyOtpState) {
            is VerifyOtpState.Success -> {
                // Navigate to the next screen, e.g., home or login
                navController.navigate("login") { popUpTo("welcome") { inclusive = true } }
            }
            is VerifyOtpState.Error -> {
                alertMessage = state.message
                showAlert = true
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(vertical = 12.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp).clickable { navController.popBackStack() }
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verify Your Email",
                color = Color.White,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter the 6-digit code sent to $email",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // OTP Input Fields
            BasicTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 6) {
                        code = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(6) {
                            val char = code.getOrNull(it)?.toString() ?: ""
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = char, color = Color.White, fontSize = 20.sp)
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Verify Button
            Button(
                onClick = {
                    if (code.length == 6) {
                        verifyOtpViewModel.verifyOtp(email, code)
                    } else {
                        alertMessage = "Please enter a 6-digit OTP."
                        showAlert = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = verifyOtpState != VerifyOtpState.Loading
            ) {
                if (verifyOtpState == VerifyOtpState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text(
                        text = "Verify",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Alert Dialog
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text("OK", color = Color.Cyan)
                    }
                },
                title = { Text("Verification Error", color = Color.White) },
                text = { Text(alertMessage, color = Color.White) },
                containerColor = Color.DarkGray
            )
        }
    }
}
