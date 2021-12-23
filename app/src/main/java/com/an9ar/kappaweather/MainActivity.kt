package com.an9ar.kappaweather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.an9ar.kappaweather.screens.MainNavScreen
import com.an9ar.kappaweather.theme.KappaWeatherTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KappaWeatherTheme {
                ProvideWindowInsets {
                    MainNavScreen(mainViewModel = mainViewModel)
                }
            }
        }
    }
}