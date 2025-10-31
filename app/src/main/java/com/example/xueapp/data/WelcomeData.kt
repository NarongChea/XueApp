package com.example.xueapp.data

import com.example.xueapp.R

sealed class WelcomeData(
    val image: Int,
    val title: String,
    val description: String
) {
    object First : WelcomeData(
        image = R.drawable.welcome1,
        title = "Welcome to XUE APP",
        description = "Explore amazing features easily."
    )

    object Second : WelcomeData(
        image = R.drawable.welcome2,
        title = "Track Your Progress",
        description = "Keep your learning journey organized."
    )

    object Third : WelcomeData(
        image = R.drawable.welcome3,
        title = "Get Started Now",
        description = "Enjoy a seamless experience."
    )

    companion object {
        val list = listOf(First, Second, Third)
    }
}
