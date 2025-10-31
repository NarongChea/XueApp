package com.example.bottomnavtest.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class User(
    val email: String,
    val password: String,
    val bookmarked: List<Int> = emptyList(),
    val markAsRead: List<Int> = emptyList(),
    val role: String = "student",
    val alreadyReady: List<Int> = emptyList(),
    val countRead: Int = 0,
    val certificate: Int = 0,
    val word: Int = 0,
    val character: Int = 0,
    val subscription: Boolean = false,
    val expireSub: String? = null
)

// -------------------------
// Global user state
// -------------------------

val allUsers = mutableListOf<User>()
val isLoggedIn = mutableStateOf(false)

// Make currentUser reactive for Compose
var currentUser by mutableStateOf<User?>(null)

// -------------------------
// Authentication functions
// -------------------------

fun signIn(email: String, password: String): Boolean {
    val user = allUsers.find { it.email == email && it.password == password }
    return if (user != null) {
        currentUser = user
        isLoggedIn.value = true
        true
    } else {
        false
    }
}

fun signUp(email: String, password: String): Boolean {
    if (allUsers.any { it.email == email }) return false
    val newUser = User(email, password)
    allUsers.add(newUser)
    currentUser = newUser
    isLoggedIn.value = true
    return true
}

fun signOut() {
    currentUser = null
    isLoggedIn.value = false
}

fun changePassword(currentPass: String, newPass: String): Boolean {
    val user = currentUser ?: return false
    return if (user.password == currentPass) {
        val updatedUser = user.copy(password = newPass)
        // Update in the allUsers list
        allUsers[allUsers.indexOf(user)] = updatedUser
        currentUser = updatedUser
        true
    } else {
        false
    }
}
