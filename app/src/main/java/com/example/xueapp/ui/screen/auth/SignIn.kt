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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.xueapp.data.TokenManager
import com.example.xueapp.viewmodel.SignInState
import com.example.xueapp.viewmodel.SignInViewModel
import com.example.xueapp.viewmodel.SignInViewModelFactory

@Composable
fun SignIn(navController: NavController) {
    val context = LocalContext.current
    val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory(context))

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val tokenManager = remember { TokenManager(context) }

    val signInState by signInViewModel.signInState.collectAsState()

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    LaunchedEffect(signInState) {
        when (val state = signInState) {
            is SignInState.Success -> {
                val accessToken = state.authResponse.data?.tokens?.accessToken
                val refreshToken = state.authResponse.data?.tokens?.refreshToken
                if (accessToken != null && refreshToken != null) {
                    tokenManager.saveTokens(accessToken, refreshToken)
                    navController.navigate("home") { popUpTo(0) }
                } else {
                    alertMessage = "Login successful, but tokens were not received."
                    showAlert = true
                }
            }
            is SignInState.Error -> {
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Cyan
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Cyan
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            // Sign In button
            Button(
                onClick = {
                    signInViewModel.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth().height(55.dp),
                enabled = signInState != SignInState.Loading
            ) {
                if (signInState == SignInState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Sign In", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("OK", color = Color.Cyan)
                }
            },
            title = { Text("Sign In Error", color = Color.White) },
            text = { Text(alertMessage, color = Color.White) },
            containerColor = Color.DarkGray
        )
    }
}
