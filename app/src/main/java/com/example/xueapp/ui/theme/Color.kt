package com.example.xueapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val LightRed = Color(0xFFE71616)
val LighterRed = Color(0xFFEC3232)
val LightWhite = Color(0xFFE35959)


val DarkWhite = Color(0xFFCECBCB)
val PopularWhite = Color(0xFFF5F5F5)
val PopularBlack = Color(0xFF1C1C1C)


val RedGradient = Brush.linearGradient(
    colors = listOf(
        LightRed,
        LighterRed,
        LightWhite,
        LighterRed,
        LightRed
    )
)
