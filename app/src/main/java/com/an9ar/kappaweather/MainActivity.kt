package com.an9ar.kappaweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.theme.KappaWeatherTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KappaWeatherTheme {
                Surface(color = AppTheme.colors.background) {

                }
            }
        }
    }
}