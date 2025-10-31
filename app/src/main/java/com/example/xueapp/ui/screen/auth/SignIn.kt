package com.example.xueapp.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bottomnavtest.data.signIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 100.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sign Up link
            val signUpText = buildAnnotatedString {
                pushStringAnnotation(tag = "SIGN_UP", annotation = "sign_up")
                withStyle(
                    style = SpanStyle(
                        color = Color.Cyan,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("No account? Sign up here for free.")
                }
                pop()
            }

            ClickableText(
                text = signUpText,
                onClick = { offset ->
                    signUpText.getStringAnnotations("SIGN_UP", start = offset, end = offset)
                        .firstOrNull()?.let { navController.navigate("signup") }
                },
                style = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp)
            )

            // Email & Password fields
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            // Sign In button
            Button(
                onClick = {
                    if (signIn(email, password)) {
                        navController.navigate("home") {
                            popUpTo(0)
                        }
                    } else {
                        showError = true
                        errorMessage = "Invalid email or password"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth().height(55.dp)
            ) {
                Text("Sign In", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showError) {
        AlertDialog(
            onDismissRequest = { showError = false },
            confirmButton = {
                TextButton(onClick = { showError = false }) {
                    Text("OK", color = Color.Cyan)
                }
            },
            title = { Text("Sign In Error", color = Color.White) },
            text = { Text(errorMessage, color = Color.White) },
            containerColor = Color.DarkGray
        )
    }
}
