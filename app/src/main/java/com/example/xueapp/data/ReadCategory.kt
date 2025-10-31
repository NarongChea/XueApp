package com.example.xueapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ReadCategory(
   val icon : ImageVector,
   val title : String
) {
    object Story : ReadCategory(Icons.Filled.Book, "Stories")
    object Articles : ReadCategory(Icons.Filled.LibraryBooks, "Articles")
    object Course : ReadCategory(Icons.Filled.VideoLibrary, "Courses")
    companion object{
        val Readlist = listOf(Story, Articles,Course)
    }
}