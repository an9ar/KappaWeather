package com.an9ar.kappaweather.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CreditsScreen(
    navHostController: NavHostController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background)) {
        AuthorCard(this, navHostController = navHostController)
        Spacer(modifier = Modifier.height(8.dp))
        SocialMediaBar(this)
        Spacer(modifier = Modifier.height(8.dp))
        AuthorInfo(this)
    }
}

@Composable
fun AuthorCard(
    scope: ColumnScope,
    navHostController: NavHostController
) {
    scope.run {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            AppTheme.colors.card,
                            AppTheme.colors.card
                        )
                    )
                )
                .statusBarsPadding()
        ) {
            val (authorPhoto, backButton) = createRefs()
            GlideImage(
                data = "https://avatars3.githubusercontent.com/u/63235125?s=460&u=49ee018d6f0b00cc7853b8d9fa79106b8b949b4b&v=4",
                contentDescription = "Author",
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .border(
                        width = 16.dp,
                        brush = SolidColor(AppTheme.colors.background),
                        shape = CircleShape
                    )
                    .constrainAs(authorPhoto) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            IconButton(
                onClick = { navHostController.navigateUp() },
                modifier = Modifier
                    .constrainAs(backButton) {
                        top.linkTo(parent.top, 8.dp)
                        start.linkTo(parent.start, 8.dp)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = AppTheme.colors.uiSurface
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
                .background(AppTheme.colors.background)
                .padding(vertical = 8.dp)
        ) {
            val context = LocalContext.current
            SocialMediaButton(
                buttonImage = painterResource(id = R.drawable.ic_social_github),
                onClick = { context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/an9ar"))) }
            )
            SocialMediaButton(
                buttonImage = painterResource(id = R.drawable.ic_social_linkedin),
                onClick = { context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://linkedin.com/in/grigorievdy"))) }
            )
            SocialMediaButton(
                buttonImage = painterResource(id = R.drawable.ic_social_telegram),
                onClick = { context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://t.me/grigorievdy"))) }
            )
        }
    }
}

@Composable
fun SocialMediaButton(
    buttonImage: Painter,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AppTheme.colors.card)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Image(
            painter = buttonImage,
            contentDescription = null,
            colorFilter = ColorFilter.tint(AppTheme.colors.uiSurface)
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
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .weight(0.5f)
            .background(AppTheme.colors.card)
            .padding(16.dp)
        ) {
            NameAndPositionBlock()
            Divider(
                color = AppTheme.colors.background,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            AboutInfoBlock()
        }
    }
}

@Composable
fun NameAndPositionBlock() {
    Text(
        text = "Dmitriy Grigoriev",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.h5,
        color = AppTheme.colors.text
    )
    Text(
        text = "Android Software Engineer",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body1,
        color = AppTheme.colors.textSecondary
    )
}

@Composable
fun AboutInfoBlock() {
    Text(
        text = "About me",
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.h5,
        color = AppTheme.colors.text
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body1,
        color = AppTheme.colors.textSecondary
    )
}
