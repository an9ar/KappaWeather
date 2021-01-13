package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun CreditsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)) {
        AuthorCard(this)
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

@Composable
fun AuthorCard(
    scope: ColumnScope
) {
    scope.run {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f)
                .background(Brush.linearGradient(colors = listOf(AppTheme.colors.background, AppTheme.colors.card)))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    data = "https://avatars3.githubusercontent.com/u/63235125?s=460&u=49ee018d6f0b00cc7853b8d9fa79106b8b949b4b&v=4",
                    loading = {
                        Box(Modifier.fillMaxSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    error = {
                        Image(bitmap = imageResource(R.drawable.ic_launcher_background))
                    },
                    modifier = Modifier
                        .preferredSize(160.dp)
                        .clip(CircleShape)
                        .border(
                            width = 16.dp,
                            brush = SolidColor(AppTheme.colors.card),
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.preferredHeight(8.dp))
                Text(text = "Version 1.0.0", color = AppTheme.colors.text)
            }
        }
    }
}