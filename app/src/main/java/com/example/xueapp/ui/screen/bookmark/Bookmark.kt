package com.example.xueapp.ui.screen.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bottomnavtest.data.currentUser
import com.example.xueapp.data.StoryList

@Composable
fun BookmarkScreen(navController: NavController) {
    val currentUser = currentUser
    val bookmarkedStories = currentUser?.let { user ->
        StoryList.stories.filter { it.id in user.bookmarked }
    } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(vertical = 30.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.navigate("home") }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bookmark",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (currentUser == null) {
            // Not logged in
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = "Please log in to see your bookmarked lessons",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(text = "Go to Login", color = Color.White)
                }
            }
        } else if (bookmarkedStories.isEmpty()) {
            // Logged in but no bookmarks
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Bookmark",
                    tint = Color.Gray,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No bookmarked lessons",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap the \"Bookmark\" icon in a lesson to save it for later.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Show bookmarked stories
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookmarkedStories) { story ->
                    BookmarkedCard(
                        timeIcon = story.TimeIcon,
                        timeText = story.TimeText,
                        imageRes = story.imageRes,
                        level = story.level,
                        spend = story.spend,
                        storyTitle = story.storyTitle
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkedCard(
    timeIcon: ImageVector,
    timeText: String,
    imageRes: Int,
    level: Int,
    spend: String,
    storyTitle: String
) {
    val (levelColor, levelText) = when (level) {
        1 -> Color(0xFF4CAF50) to "Beginner"
        2 -> Color(0xFF2196F3) to "Novice"
        3 -> Color(0xFFFFC107) to "Skilled"
        4 -> Color(0xFFFF9800) to "Experienced"
        5 -> Color(0xFFF44336) to "Expert"
        6 -> Color(0xFF9C27B0) to "Master"
        7 -> Color(0xFF00BCD4) to "Grandmaster"
        else -> Color.Gray to "Unknown"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rectangle image on the left
        Box(
            modifier = Modifier
                .width(125.dp)
                .height(100.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = storyTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )

            if (spend == "true") {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(20.dp)
                        .background(Color(0x80000000), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Premium",
                        tint = Color.Yellow,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Text side: title, level, time
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = storyTitle,
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(levelColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = levelText,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = timeIcon,
                    contentDescription = "Time",
                    tint = Color(0xFF68DCA2),
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = timeText,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}
