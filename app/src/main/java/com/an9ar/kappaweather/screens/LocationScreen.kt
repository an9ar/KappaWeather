package com.an9ar.kappaweather.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.add
import dev.chrisbanes.accompanist.insets.toPaddingValues

@Composable
fun LocationsScreen(
        mainViewModel: MainViewModel,
        navHostController: NavHostController
) {
    val locationCities = mainViewModel.locationsList.observeAsState(initial = emptyList())
    Scaffold(
            topBar = {
                TopAppBar(backgroundColor = AppTheme.colors.toolbar) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (screenTitle, backButton) = createRefs()
                        Text(
                                text = "Locations managing",
                                style = AppTheme.typography.h6,
                                color = AppTheme.colors.text,
                                modifier = Modifier.constrainAs(screenTitle) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )
                    }
                }
            }
    ) {
        LocationsScreenContent(
                navHostController = navHostController,
                selectedCities = locationCities.value
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationsScreenContent(
        navHostController: NavHostController,
        selectedCities: List<CityModel>
) {
    Surface(
            color = AppTheme.colors.background,
            modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
                contentPadding = AmbientWindowInsets.current.systemBars
                        .toPaddingValues(bottom = false)
                        .add(bottom = AppTheme.sizes.bottomNavigationHeight)
        ) {
            stickyHeader {
                CityListItemHeader(title = "New locations")
            }
            item {
                AddNewListItem(navHostController = navHostController)
            }

            if (selectedCities.isNotEmpty()) {
                stickyHeader {
                    CityListItemHeader(title = "Selected cities")
                }
                items(items = selectedCities) { city ->
                    ListItem(cityModel = city)
                }
            }
        }
    }
}

@Composable
fun AddNewListItem(navHostController: NavHostController) {
    Card(
            backgroundColor = AppTheme.colors.card,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable(onClick = {
                        navHostController.navigate(Screens.CountryChooseScreen.routeName)
                    })
                    .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                    text = "Add new location",
                    color = AppTheme.colors.text,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.listItem,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}

@Composable
fun ListItem(
        cityModel: CityModel
) {
    Card(
            backgroundColor = AppTheme.colors.card,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable(onClick = {

                    })
                    .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                    text = cityModel.name,
                    color = AppTheme.colors.text,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.listItem,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}
