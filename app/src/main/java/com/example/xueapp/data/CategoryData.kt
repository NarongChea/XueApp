package com.example.xueapp.data

sealed class CategoryData (
    val num : String,
    val lesson : String
){
    object lessons : CategoryData("6","Lessons")
    object words : CategoryData("123","Words")
    object characters : CategoryData("12","Character")
    companion object {
        val categories = listOf(lessons, words, characters)
    }
}