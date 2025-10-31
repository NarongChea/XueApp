package com.example.xueapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class LibraryData(
    val icon: ImageVector?,
    val text: String,
    val color: Color
) {
    object Bookmarked : LibraryData(Icons.Filled.Bookmark, "Bookmarked", Color(0xFFE4ECE4))
    object MarkedRead : LibraryData(Icons.Filled.CheckCircle, "Marked as read", Color(0xF75EEA5E))
    object Downloaded : LibraryData(Icons.Filled.CloudDownload, "Downloaded", Color(0xFF3E9BDC))

    companion object {
        val libraryItems = listOf(Bookmarked, MarkedRead, Downloaded)
    }
}
