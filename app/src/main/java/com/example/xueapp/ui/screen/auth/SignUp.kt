package com.example.xueapp.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bottomnavtest.data.User
import com.example.bottomnavtest.data.signUp

@Composable
fun SignUp(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeChecked by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("yes") }

    // For showing alert dialogs
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp).clickable { navController.popBackStack() }
            )
            Text(
                text = "Sign Up",
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 110.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Description & login link
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Enter your email address and choose a password to create a free account.",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Any existing data that you have will be associated with the new account.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
                val loginText = buildAnnotatedString {
                    pushStringAnnotation(tag = "LOGIN", annotation = "login")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Cyan,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Already have an account? Login here.")
                    }
                    pop()
                }
                ClickableText(
                    text = loginText,
                    onClick = { offset ->
                        loginText.getStringAnnotations("LOGIN", offset, offset)
                            .firstOrNull()?.let { navController.navigate("signin") }
                    },
                    style = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp)
                )
            }

            // Email & Password
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

            // Terms checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = agreeChecked,
                    onCheckedChange = { agreeChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Cyan,
                        uncheckedColor = Color.White
                    )
                )
                Text(
                    text = "I agree to the Terms of Service and Privacy Policy. (Required)",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Optional updates
            Text(
                text = "We'd love to send you exclusive discounts, study tips, and occasional updates about what's going on.",
                color = Color.White,
                fontSize = 14.sp
            )

            // Radio Buttons
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedOption = "yes" }
                ) {
                    RadioButton(
                        selected = selectedOption == "yes",
                        onClick = { selectedOption = "yes" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Cyan)
                    )
                    Text(
                        text = "Yes, I'd like to hear about study tips, discounts, and occasional updates.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedOption = "no" }
                ) {
                    RadioButton(
                        selected = selectedOption == "no",
                        onClick = { selectedOption = "no" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Cyan)
                    )
                    Text(
                        text = "No, I don't want to hear about study tips, discounts, and occasional updates.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            // Sign Up Button
            Button(
                onClick = {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            alertMessage = "Please enter both email and password."
                            showAlert = true
                        }
                        !agreeChecked -> {
                            alertMessage = "You must agree to the Terms of Service and Privacy Policy."
                            showAlert = true
                        }
                        else -> {
                            signUp(email, password)
                            navController.navigate("home") { popUpTo(0) }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
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
                title = { Text("Sign Up Error", color = Color.White) },
                text = { Text(alertMessage, color = Color.White) },
                containerColor = Color.DarkGray
            )
        }
    }
}

