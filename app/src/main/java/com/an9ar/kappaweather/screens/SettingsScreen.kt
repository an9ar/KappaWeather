package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)) {
        Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                        .fillMaxSize()
                        .weight(0.4f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                        imageVector = vectorResource(id = R.drawable.ic_kappa_sign),
                        colorFilter = ColorFilter.tint(AppTheme.colors.text),
                        modifier = Modifier.preferredSize(64.dp)
                )
                Spacer(modifier = Modifier.preferredHeight(8.dp))
                Text(text = "Version 1.0.0", color = AppTheme.colors.text)
            }
        }
        Column(Modifier.background(AppTheme.colors.warning).fillMaxSize().weight(0.6f)) {

        }
    }
}