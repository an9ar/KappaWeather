package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.theme.AppTheme

@Composable
fun TestScreen(color: Color) {
    Box(modifier = Modifier.fillMaxSize().background(color), contentAlignment = Alignment.Center) {
        NeumorphButton()
    }
}

@Composable
fun NeumorphButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .preferredSize(100.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = AppTheme.colors.card,
            modifier = Modifier
                .preferredSize(64.dp)
                .padding(8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ).then(Modifier.background(color = Color.Red))

        ) {

        }
    }
}