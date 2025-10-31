package com.example.xueapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color(0xFFA19A9A),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        // 🏠 Home
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(32.dp)) },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )

        // 📚 Read
        NavigationBarItem(
            icon = { Icon(Icons.Default.Book, contentDescription = "Read", modifier = Modifier.size(32.dp)) },
            label = { Text("Read") },
            selected = currentRoute == "read_screen",
            onClick = {
                if (currentRoute != "read_screen") {
                    navController.navigate("read_screen") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )

        // 👤 Account
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Account", modifier = Modifier.size(32.dp)) },
            label = { Text("Account") },
            selected = currentRoute == "account",
            onClick = {
                if (currentRoute != "account") {
                    // Navigate to AccountScreen without clearing back stack
                    navController.navigate("account") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
    }
}
