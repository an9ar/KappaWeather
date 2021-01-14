package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun CreditsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)) {
        AuthorCard(this)
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        SocialMediaBar(this)
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        AuthorInfo(this)
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
                .clip(RoundedCornerShape(bottomLeft = 16.dp, bottomRight = 16.dp))
                .background(Brush.linearGradient(colors = listOf(AppTheme.colors.background, AppTheme.colors.card)))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.statusBarsPadding()
            ) {
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
                        .padding(16.dp)
                        .clip(CircleShape)
                        .border(
                            width = 16.dp,
                            brush = SolidColor(AppTheme.colors.card),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun SocialMediaBar(
    scope: ColumnScope
) {
    scope.run { 
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colors.card)
                .padding(vertical = 8.dp)
        ) {
            SocialMediaButton(buttonImage = vectorResource(id = R.drawable.ic_kappa_sign))
            SocialMediaButton(buttonImage = vectorResource(id = R.drawable.ic_kappa_sign))
            SocialMediaButton(buttonImage = vectorResource(id = R.drawable.ic_kappa_sign))
        }
    }
}

@Composable
fun SocialMediaButton(
    buttonImage: ImageVector
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AppTheme.colors.background)
            .clickable{}
            .padding(8.dp)
    ) {
        Image(
            imageVector = buttonImage,
            colorFilter = ColorFilter.tint(AppTheme.colors.card)
        )
    }
}

@Composable
fun AuthorInfo(
    scope: ColumnScope
) {
    scope.run {
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(0.5f)
            .clip(RoundedCornerShape(topLeft = 16.dp, topRight = 16.dp))
            .background(AppTheme.colors.card)
            .padding(16.dp)
        ) {
            Text(
                text = "Dmitriy Grigoriev",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Android Software Engineer",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "About me",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
