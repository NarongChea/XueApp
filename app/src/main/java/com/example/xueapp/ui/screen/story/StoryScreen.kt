package com.example.xueapp.ui.screen.story

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.bottomnavtest.data.allUsers
import com.example.bottomnavtest.data.currentUser
import com.example.bottomnavtest.data.User
import com.example.xueapp.data.StoryList

@Composable
fun StoryScreen(storyId: Int?, navController: NavController) {
    val story = StoryList.stories.firstOrNull { it.id == storyId } ?: return
    val scrollState = rememberScrollState()

    var isBookmarked by remember {
        mutableStateOf(currentUser?.bookmarked?.contains(story.id) ?: false)
    }

    var isRead by remember {
        mutableStateOf(currentUser?.markAsRead?.contains(story.id) ?: false)
    }

    val (levelColor, levelText) = when (story.level) {
        1 -> Color(0xFF4CAF50) to "Beginner"
        2 -> Color(0xFF2196F3) to "Novice"
        3 -> Color(0xFFFFC107) to "Skilled"
        4 -> Color(0xFFFF9800) to "Experienced"
        5 -> Color(0xFFF44336) to "Expert"
        6 -> Color(0xFF9C27B0) to "Master"
        7 -> Color(0xFF00BCD4) to "Grandmaster"
        else -> Color.Gray to "Unknown"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 80.dp, bottom = 160.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = story.imageRes),
                    contentDescription = story.storyTitle,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Story Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = story.storyTitle,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(levelColor, shape = CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = levelText,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = story.description,
                color = Color(0xFFDDDDDD),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            // Icons Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bookmark toggle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        val user = currentUser
                        if (user == null) {
                            navController.navigate("login")
                        } else {
                            isBookmarked = !isBookmarked
                            val updatedUser = if (isBookmarked) {
                                user.copy(bookmarked = user.bookmarked + story.id)
                            } else {
                                user.copy(bookmarked = user.bookmarked - story.id)
                            }
                            currentUser = updatedUser
                            allUsers[allUsers.indexOf(user)] = updatedUser
                        }
                    }
                ) {
                    Icon(
                        if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) Color.Yellow else Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Text("Bookmark", color = Color.White, fontSize = 14.sp)
                }

                // Download
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate("downloaded") }
                ) {
                    Icon(Icons.Default.Download, "Download", tint = Color.White, modifier = Modifier.size(30.dp))
                    Text("Download", color = Color.White, fontSize = 14.sp)
                }

                // Mark as Read toggle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        val user = currentUser
                        if (user == null) {
                            navController.navigate("login")
                        } else {
                            isRead = !isRead
                            val updatedUser = if (isRead) {
                                user.copy(markAsRead = user.markAsRead + story.id)
                            } else {
                                user.copy(markAsRead = user.markAsRead - story.id)
                            }
                            currentUser = updatedUser
                            allUsers[allUsers.indexOf(user)] = updatedUser
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        "Mark as Read",
                        tint = if (isRead) Color.Green else Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Text("Mark as Read", color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        // Sticky Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .align(Alignment.TopCenter)
                .zIndex(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = story.storyTitle,
                    color = Color.White,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Fixed Bottom Buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 25.dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Read & Listen Button
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Read & Listen",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Audiobook Button
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292A2A)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(50))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Headphones, "Audiobook", tint = Color.Red, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Audiobook", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
