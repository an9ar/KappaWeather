package com.an9ar.kappaweather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.an9ar.kappaweather.screens.MainNavScreen
import com.an9ar.kappaweather.theme.KappaWeatherTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KappaWeatherTheme{
                ProvideWindowInsets {
                    MainNavScreen(mainViewModel = mainViewModel)
                }
            }
        }
    }
}