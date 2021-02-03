package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme

@Composable
fun MenuScreen(navHostController: NavHostController) {
    MenuScreenContent(navHostController = navHostController)
}

@Composable
fun MenuScreenContent(
    navHostController: NavHostController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.background)) {
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
                    contentDescription = "App logo",
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
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .weight(0.6f)
        ) {
            MenuListItem(
                vectorImage = vectorResource(id = R.drawable.ic_kappa_sign),
                itemTitle = "Locations managing",
                onClickAction = { navHostController.navigate(Screens.LocationsScreen.routeName) }
            )
            MenuListItem(
                vectorImage = vectorResource(id = R.drawable.ic_kappa_sign),
                itemTitle = "Settings",
                onClickAction = { }
            )
            MenuListItem(
                vectorImage = vectorResource(id = R.drawable.ic_kappa_sign),
                itemTitle = "Credits",
                onClickAction = { navHostController.navigate(Screens.CreditsScreen.routeName) }
            )
        }
    }
}

@Composable
fun MenuListItem(
    vectorImage: ImageVector,
    itemTitle: String,
    onClickAction: () -> Unit
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable(onClick = {
                onClickAction()
            })
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                imageVector = vectorImage,
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppTheme.colors.text),
                modifier = Modifier
                    .preferredSize(16.dp)
                    .weight(0.1f)
            )
            Text(
                text = itemTitle,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.9f),
            )
        }
    }
}