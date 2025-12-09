package com.example.xueapp.ui.screen.home

import com.example.xueapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.xueapp.data.CategoryData
import com.example.xueapp.data.GuideData
import com.example.xueapp.data.LibraryData
import com.example.xueapp.data.SettingList
import com.example.xueapp.data.TokenManager
import com.example.xueapp.viewmodel.HomeViewModel
import com.example.xueapp.viewmodel.HomeViewModelFactory
import com.example.xueapp.viewmodel.UserState

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(context))
    val userState by homeViewModel.userState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (userState) {
            is UserState.Loading -> {
                CircularProgressIndicator(color = Color.White)
            }
            is UserState.Success -> {
                val user = (userState as UserState.Success).user
                HomeScreenContent(navController, modifier)
            }
            is UserState.Error -> {
                val message = (userState as UserState.Error).message
                // Handle error state, maybe show a toast or a dialog
                // For now, let's just log out the user
                val tokenManager = TokenManager(context)
                tokenManager.clearTokens()
                navController.navigate("login") {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }
    }
}


@Composable
fun HomeScreenContent(navController: NavController,modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val LightBlue = Color(0xFF91CDDE)
            val LightRed = Color(0xFFE71616)
            val LighterRed = Color(0xFFEC3232)
            val LightWhite = Color(0xFFE35959)
            val gradient = Brush.linearGradient(
                colors = listOf(
                    LightRed,
                    LighterRed,
                    LightWhite ,
                    LighterRed,
                    LightRed
                )
            )
            val DarkWhite = Color(0xFFCECBCB)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding( horizontal = 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(brush = gradient)
                            .border(1.dp, Color.White, RoundedCornerShape(12.dp))
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Xue App",
                            color = Color.White,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }

                // Section Header
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Reading Progress", color = DarkWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "See more",
                            modifier = Modifier.clickable {},
                            color = LightBlue
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }

                // Progress Row
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                                .padding(15.dp)
                        ) {
                            Row(
                                modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CategoryData.categories.forEach { category ->
                                    ReadingProgress(category.num, category.lesson)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "My library", color = DarkWhite, fontSize = 22.sp,fontWeight= FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
                item() {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LibraryData.libraryItems.forEach { item ->
                            ShowCategory(
                                icon = item.icon,
                                text = item.text,
                                color = item.color,
                                onClick = {
                                    // navigate based on the item
                                    when (item) {
                                        LibraryData.Bookmarked -> navController.navigate("bookmarked")
                                        LibraryData.MarkedRead -> navController.navigate("marked_read")
                                        LibraryData.Downloaded -> navController.navigate("downloaded")
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
                item(){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ){
                        GuideData.guidelist.forEach {
                                guide->GuideContent(guide.title,guide.description,guide.color,modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Settings,
                            contentDescription = "Setting",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp))
                        Text(text = "Setting", color = DarkWhite, fontSize = 22.sp,fontWeight= FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
                item() {
                    Column(){
                        SettingList.settingitems.forEach { item ->
                            ShowCategory( icon = null,
                                text = item.text,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                }
            }
        }
    }


@Composable
fun ReadingProgress(num: String, lesson: String) {
    val darkWhite = Color(0xFFEAE9E9)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            text = num,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = lesson,
            color = darkWhite,
            fontSize = 18.sp
        )
    }
}


@Composable
fun ShowCategory(
    icon: ImageVector? = null,
    text: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    onClick: (() -> Unit)? = null // ✅ add click lambda
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ), // ✅ make row clickable
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun GuideContent(title: String, description: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, color, RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.4f))
            .height(150.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)){
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }
    }
}
