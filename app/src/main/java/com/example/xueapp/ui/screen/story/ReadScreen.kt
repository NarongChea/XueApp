package com.example.xueapp.ui.screen.story

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.xueapp.data.ReadCategory
import com.example.xueapp.data.StoryList
import com.example.xueapp.ui.component.BottomNavBar

@Composable
fun ReadScreen(navController: NavController,modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var search by remember { mutableStateOf(TextFieldValue("")) }
            val isSearching = search.text.isNotEmpty()

            val filteredStories = StoryList.stories.filter {
                it.storyTitle.contains(search.text, ignoreCase = true)
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                item{
                    Text(text="What to read next?", fontSize = 24.sp,color=Color.White, modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        TextField(
                            value = search,
                            onValueChange = { newValue -> search = newValue },
                            placeholder = { Text("Search for titles", color = Color.Gray) },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                focusedContainerColor = Color.DarkGray,
                                unfocusedContainerColor = Color.DarkGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .background(Color.DarkGray, RoundedCornerShape(10.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Search Results
                if (isSearching) {
                    val trimmedSearch = search.text.trim()
                    if (filteredStories.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No results found", color = Color.Gray, fontSize = 16.sp)
                            }
                        }
                    } else {

                        items(filteredStories) { story ->
                            SearchRowStory(
                                story.id,
                                story.TimeIcon,
                                story.TimeText,
                                story.imageRes,
                                story.level,
                                story.type,
                                story.spend,
                                story.storyTitle,
                                story.description,
                                navController = navController
                            )
                            Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 0.5.dp)
                        }

                    }
                } else {

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            ReadCategory.Readlist.forEach { item ->
                                ReadCat(item.icon, item.title, modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    repeat(4){
                        item {
                            Text(
                                text = "Top Picks for you",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(13.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))
                                StoryList.stories.forEach { story ->
                                    StoryCard(
                                        id = story.id,
                                        timeIcon = story.TimeIcon,
                                        timeText = story.TimeText,
                                        imageRes = story.imageRes,
                                        level = story.level,
                                        type = story.type,
                                        spend = story.spend,
                                        storyTitle = story.storyTitle,
                                        description = story.description,
                                        navController = navController
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                            }
                            Spacer(modifier = Modifier.height(50.dp))
                        }
                    }

                }
            }
        }
    }
@Composable
fun SearchRowStory(
    id : Int,
    timeIcon: ImageVector,
    timeText: String,
    imageRes: Int,
    level: Int,
    type: String,
    spend: String,
    storyTitle: String,
    description: String,
    navController: NavController
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
            .clickable { navController.navigate("story/${id}") }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.DarkGray.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.width(100.dp).height(100.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = storyTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            if (spend == "true") {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .background(Color(0x80000000), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Star, contentDescription = "Premium", tint = Color.Yellow, modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(levelColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(levelText, fontSize = 13.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(storyTitle, fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(2.dp))
            Text(type, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun ReadCat(icon: ImageVector, title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x5C3C9665))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF68DCA2),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = title, color = Color.White)
        }
    }
}

@Composable
fun StoryCard(
    id: Int,
    timeIcon: ImageVector,
    timeText: String,
    imageRes: Int,
    level: Int,
    type: String,
    spend: String,
    storyTitle: String,
    description: String,
    navController: NavController
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

    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                // 🔹 Pass story ID to StoryScreen
                navController.navigate("story/${id}")
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(timeIcon, contentDescription = type, tint = Color(0xFF68DCA2), modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(timeText, fontSize = 14.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = storyTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop
            )

            if (spend == "true") {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(26.dp)
                        .background(Color(0x80000000), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Star, contentDescription = "Premium", tint = Color.Yellow, modifier = Modifier.size(17.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(10.dp).background(levelColor, shape = CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
            Text(levelText, fontSize = 14.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(3.dp))
        Text(type, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(3.dp))
        Text(storyTitle, fontSize = 18.sp, color = Color.White)
    }
}
