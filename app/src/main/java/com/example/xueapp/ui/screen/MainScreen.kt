package com.example.xueapp.ui.screen

import com.example.bottomnavtest.BottomBarScreen
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.xueapp.ui.navigation.BottomNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            if (currentRoute in listOf(
                    BottomBarScreen.Home.route,
                    BottomBarScreen.Read.route,
                    BottomBarScreen.Profile.route
                )
            ) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        BottomNavGraph(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screen = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Read,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        screen.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.route,
                tint = if (selected) Color.Red else Color.White // red active, white inactive
            )
        },
        label = {
            Text(
                text = screen.route,
                color = if (selected) Color.Red else Color.White // red active, white inactive
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
