package com.an9ar.kappaweather.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColors {
    val primary = Color(0xFF3366FF)
    val background = Color(0xFFE7E7E7)
    val backgroundReverse = Color(0xFF141414)
    val toolbar = Color(0xFFE7E7E7)
    val toolbarReverse = Color(0xFF141414)
    val card = Color(0xFFFFFFFF)
    val cardReverse = Color(0xFF252525)
    val overflowCard = Color(0x33111111)
    val overflowCardReverse = Color(0x33EEEEEE)
    val text = Color(0xFF111111)
    val textSecondary = Color(0xFF333333)
    val textReverse = Color(0xFFEEEEEE)
    val textReverseSecondary = Color(0xFFCCCCCC)
    val bottomNavItem = Color(0xFF000000)
    val bottomNavItemReverse = Color(0xFFFFFFFF)
    val success = Color(0xFF00E096)
    val warning = Color(0xFFFFAA00)
    val error = Color(0xFFFF3D31)
    val light = Color(0xFFEEEEEE)
    val dark = Color(0xFF1E1E1E)
    val transparent = Color(0x00000000)
}

object WeatherColors {
    val white = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFFFFFFF)
        )
    )
    val clearSkyDay = Brush.linearGradient(
        colors = listOf(
            Color(0xFFC7E6FE),
            Color(0xFF94C9F2),
            Color(0xFF65ABE2)
        )
    )
    val clearSkyNight = Brush.linearGradient(
        colors = listOf(
            Color(0xFF3F3F64),
            Color(0xFF75658A),
            Color(0xFFCB8E96)
        )
    )
    val fewCloudsDay = Brush.linearGradient(
        colors = listOf(
            Color(0xFFD9E6ED),
            Color(0xFFBEDEE9),
            Color(0xFF85D0E3)
        )
    )
    val fewCloudsNight = Brush.linearGradient(
        colors = listOf(
            Color(0xFF3F3F64),
            Color(0xFF64558A),
            Color(0xFF997A98)
        )
    )
    val scatteredCloudsDay = Brush.linearGradient(
        colors = listOf(
            Color(0xFF0761B1),
            Color(0xFFC5CEDA),
            Color(0xFF7C9DCA),
            Color(0xFFC5CEDA)
        )
    )
    val scatteredCloudsNight = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5D4966),
            Color(0xFFA86978),
            Color(0xFF7F5263),
            Color(0xFFE77A6A)
        )
    )
    val snowDay = Brush.linearGradient(
        colors = listOf(
            Color(0xFF96B3DC),
            Color(0xFFF4F9FD),
            Color(0xFFF4F9FD)
        )
    )
    val snowNight = Brush.linearGradient(
        colors = listOf(
            Color(0xFF404461),
            Color(0xFF9295A7),
            Color(0xFFF7F7F9)
        )
    )
    val mistDay = Brush.linearGradient(
        colors = listOf(
            Color(0xFF909098),
            Color(0xFFC1C3CC),
            Color(0xFF91969C)
        )
    )
}

interface ColorPalette {
    val primary: Color
    val background: Color
    val toolbar: Color
    val card: Color
    val overflowCard: Color
    val text: Color
    val textSecondary: Color
    val bottomNavItem: Color
    val uiSurface: Color
    val success: Color
    val warning: Color
    val error: Color
    val light: Color
    val dark: Color
    val transparent: Color

    val materialColors: Colors
}

fun lightColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color = AppColors.primary
    override val background: Color = AppColors.background
    override val toolbar: Color = AppColors.toolbar
    override val card: Color = AppColors.card
    override val overflowCard: Color = AppColors.overflowCardReverse
    override val text: Color = AppColors.text
    override val textSecondary: Color = AppColors.textSecondary
    override val bottomNavItem: Color = AppColors.bottomNavItem
    override val uiSurface: Color = AppColors.dark
    override val success: Color = AppColors.success
    override val warning: Color = AppColors.warning
    override val error: Color = AppColors.error
    override val light: Color = AppColors.light
    override val dark: Color = AppColors.dark
    override val transparent: Color = AppColors.transparent

    override val materialColors: Colors = lightColors(
        primary = AppColors.primary,
        surface = AppColors.backgroundReverse,
        onSurface = AppColors.textReverse
    )
}

fun darkColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color = AppColors.primary
    override val background: Color = AppColors.backgroundReverse
    override val toolbar: Color = AppColors.toolbarReverse
    override val card: Color = AppColors.cardReverse
    override val overflowCard: Color = AppColors.overflowCard
    override val text: Color = AppColors.textReverse
    override val textSecondary: Color = AppColors.textReverseSecondary
    override val bottomNavItem: Color = AppColors.bottomNavItemReverse
    override val uiSurface: Color = AppColors.light
    override val success: Color = AppColors.success
    override val warning: Color = AppColors.warning
    override val error: Color = AppColors.error
    override val light: Color = AppColors.light
    override val dark: Color = AppColors.dark
    override val transparent: Color = AppColors.transparent

    override val materialColors: Colors = lightColors(
        primary = AppColors.primary,
        surface = AppColors.background,
        onSurface = AppColors.textReverse
    )
}