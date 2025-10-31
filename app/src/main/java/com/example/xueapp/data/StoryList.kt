package com.example.xueapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.xueapp.R

sealed class StoryList(
    val id: Int, // ✅ added id
    val TimeIcon: ImageVector,
    val TimeText: String,
    val imageRes: Int,
    val level: Int,
    val type: String,
    val spend: String,
    val storyTitle: String,
    val description: String // ✅ existing
) {
    object Story1 : StoryList(
        id = 1,
        TimeIcon = Icons.Filled.AccessTime,
        TimeText = "10 min read",
        imageRes = R.drawable.caocao,
        level = 1,
        type = "Historical Story",
        spend = "false",
        storyTitle = "Cao Cao: The Ambitious Warlord",
        description = "Cao Cao was a powerful warlord and poet during the late Eastern Han dynasty. Known for his ambition and strategy, he played a key role in laying the foundation for the state of Wei."
    )

    object Story2 : StoryList(
        id = 2,
        TimeIcon = Icons.Filled.AccessTime,
        TimeText = "6 min read",
        imageRes = R.drawable.zhugeliang,
        level = 2,
        type = "Wisdom Story",
        spend = "true",
        storyTitle = "Zhuge Liang: The Sleeping Dragon",
        description = "Zhuge Liang, also called Kongming, was a master strategist and inventor who served Liu Bei. His intelligence and foresight made him one of the most respected figures in Chinese history."
    )

    object Story3 : StoryList(
        id = 3,
        TimeIcon = Icons.Filled.AccessTime,
        TimeText = "7 min read",
        imageRes = R.drawable.liubei,
        level = 3,
        type = "Brotherhood Story",
        spend = "false",
        storyTitle = "Liu Bei: The Virtuous Leader",
        description = "Liu Bei was known for his kindness and loyalty. Together with Guan Yu and Zhang Fei, he founded the Shu Han kingdom and embodied the values of honor and brotherhood."
    )

    object Story4 : StoryList(
        id = 4,
        TimeIcon = Icons.Filled.AccessTime,
        TimeText = "8 min read",
        imageRes = R.drawable.sunquan,
        level = 4,
        type = "Leadership Story",
        spend = "false",
        storyTitle = "Sun Quan: The Young Emperor of Wu",
        description = "Sun Quan became the ruler of the Wu kingdom at a young age. Through wisdom and courage, he maintained stability and expanded his rule in Southern China."
    )

    object Story5 : StoryList(
        id = 5,
        TimeIcon = Icons.Filled.AccessTime,
        TimeText = "6 min read",
        imageRes = R.drawable.guanyu,
        level = 5,
        type = "Loyalty Story",
        spend = "false",
        storyTitle = "Guan Yu: The God of War",
        description = "Guan Yu was a legendary general celebrated for his loyalty and bravery. His deeds made him a symbol of righteousness, and he was later deified as the God of War."
    )

    companion object {
        val stories = listOf(Story1, Story2, Story3, Story4, Story5)
    }
}
