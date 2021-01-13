package com.an9ar.kappaweather

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.an9ar.kappaweather.screens.CreditsScreen
import com.an9ar.kappaweather.theme.KappaWeatherTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KappaWeatherTheme {
                /*SplashScreen(
                    mainViewModel = mainViewModel,
                    onDataReceivingFinish = {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME)
                        startActivity(intent)
                        this.finish()
                    }
                )*/
                CreditsScreen()
            }
        }
    }
}