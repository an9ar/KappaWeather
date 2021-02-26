package com.an9ar.kappaweather.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun KappaWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: AppTypography = AppTypography(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) darkColorPalette() else lightColorPalette()
    CompositionLocalProvider(
        AmbientColor provides colors,
        AmbientTypography provides typography,
    ) {
        MaterialTheme(
            colors = colors.materialColors,
            typography = typography.materialTypography
        ) {
            content()
        }
    }
}

object AppTheme {
    @Composable
    val colors: ColorPalette
        get() = AmbientColor.current

    @Composable
    val typography: AppTypography
        get() = AmbientTypography.current

    @Composable
    val sizes: AppSizes
        get() = AppSizes()
}

internal val AmbientColor = staticCompositionLocalOf { lightColorPalette() }
internal val AmbientTypography = staticCompositionLocalOf { AppTypography() }