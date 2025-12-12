package com.example.xueapp.ui.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OtpScreen(navController: NavController) {
    var otpCode by remember { mutableStateOf("") }
    val isOtpValid = otpCode.length == 6

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Verify OTP",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Enter the 6-digit code sent to your email or phone.",
                color = Color.LightGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = otpCode,
                onValueChange = { if (it.length <= 6) otpCode = it },
                textStyle = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    letterSpacing = 8.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                placeholder = {
                    Text(
                        text = "______",
                        color = Color.Gray,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    // TODO: validate OTP logic here
                    if (isOtpValid) {
                        navController.navigate("home")
                    }
                },
                enabled = isOtpValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isOtpValid) Color(0xFF91CDDE) else Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Verify", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
