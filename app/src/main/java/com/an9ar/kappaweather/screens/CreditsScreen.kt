package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme

@Composable
fun CreditsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize()
                .weight(0.4f)
                .background(AppTheme.colors.card)
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
        Divider(
            color = AppTheme.colors.card,
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .weight(0.6f)
        ) {

        }
    }
}