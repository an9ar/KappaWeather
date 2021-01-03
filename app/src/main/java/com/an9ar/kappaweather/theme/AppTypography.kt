package com.an9ar.kappaweather.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypography internal constructor(
        val paragraph1: TextStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
        ),
        val materialTypography: Typography = Typography(
                body1 = paragraph1
        )
)