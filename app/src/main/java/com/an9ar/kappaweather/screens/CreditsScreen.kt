package com.an9ar.kappaweather.screens

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun CreditsScreen(
    navHostController: NavHostController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background)) {
        AuthorCard(this, navHostController = navHostController)
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        SocialMediaBar(this)
        Spacer(modifier = Modifier.preferredHeight(8.dp))
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
                .clip(RoundedCornerShape(bottomLeft = 16.dp, bottomRight = 16.dp))
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
                        bitmap = imageResource(R.drawable.ic_launcher_background),
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
            SocialMediaButton(
                buttonImage = vectorResource(id = R.drawable.ic_social_github),
                onClick = {}
            )
            SocialMediaButton(
                buttonImage = vectorResource(id = R.drawable.ic_social_linkedin),
                onClick = {}
            )
            SocialMediaButton(
                buttonImage = vectorResource(id = R.drawable.ic_social_telegram),
                onClick = {}
            )
        }
    }
}

@Composable
fun SocialMediaButton(
    buttonImage: ImageVector,
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
            imageVector = buttonImage,
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
            .clip(RoundedCornerShape(topLeft = 16.dp, topRight = 16.dp))
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
    Spacer(modifier = Modifier.preferredHeight(8.dp))
    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        modifier = Modifier.fillMaxWidth(),
        style = AppTheme.typography.body1,
        color = AppTheme.colors.textSecondary
    )
    /*ClickableText(
        text = AnnotatedString(
            text = "test"
        ),
        onClick = {  }
    )*/
}
