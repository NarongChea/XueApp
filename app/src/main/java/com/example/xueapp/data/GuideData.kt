package com.example.xueapp.data

import androidx.compose.ui.graphics.Color

sealed class GuideData(
    val title : String,
    val description : String,
    val color : Color
){
    object AppGuide : GuideData("App Guide","Finding your way around the app",color = Color(0xFF006400))
    object LessonGuide : GuideData("App Guide","Tip for learning efficiently with Xue Chinese",color = Color(0xFF00008B))
    companion object {
        val guidelist = listOf(AppGuide, LessonGuide)
    }
}
