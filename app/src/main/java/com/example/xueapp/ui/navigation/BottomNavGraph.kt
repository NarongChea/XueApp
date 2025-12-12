package com.example.xueapp.ui.navigation

import com.example.bottomnavtest.BottomBarScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.xueapp.ui.screen.GuideScreen.AppGuideScreen
import com.example.xueapp.ui.screen.account.AccountScreen
import com.example.xueapp.ui.screen.auth.SignIn
import com.example.xueapp.ui.screen.auth.SignUp
import com.example.xueapp.ui.screen.bookmark.BookmarkScreen
import com.example.xueapp.ui.screen.download.DownloadScreen
import com.example.xueapp.ui.screen.home.HomeScreen
import com.example.xueapp.ui.screen.splash.SplashScreen
import com.example.xueapp.ui.screen.story.MarkAsReadScreen
import com.example.xueapp.ui.screen.story.ReadScreen
import com.example.xueapp.ui.screen.story.StoryScreen
import com.example.xueapp.ui.screen.welcome.WelcomeScreen


@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "Welcome"
    ) {
        composable(route = "welcome") {
            WelcomeScreen (navController)
        }
        composable(route = "splash") {
            SplashScreen(navController)
        }
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController,modifier)
        }
        composable(route = BottomBarScreen.Read.route) {
           ReadScreen(navController,modifier)
        }
        composable(route = BottomBarScreen.Profile.route) {
            AccountScreen(navController,modifier)
        }
        composable(route = "bookmarked") {
            BookmarkScreen(navController)
        }
        composable(route = "marked_read") {
            MarkAsReadScreen(navController)
        }
        composable(route = "download") {
            DownloadScreen(navController)
        }
        composable(route = "login") {
            SignIn(navController)
        }
        composable(route = "signup") {
            SignUp(navController)
        }
        composable(route = "app_guide_details") {
            AppGuideScreen(navController)
        }
        composable(
            route = "story/{storyId}",
            arguments = listOf(navArgument("storyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val storyId = backStackEntry.arguments?.getInt("storyId")
            StoryScreen(storyId, navController)
        }
    }
}

