package com.an9ar.kappaweather.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun CityChooseScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    countryId: String
) {
    val listOfLargestCities by mainViewModel.citiesList.observeAsState(emptyList())
    Scaffold(
        topBar = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.toolbar)
                    .padding(rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars))
                    .height(AppTheme.sizes.appBarHeight)
            ) {
                val (screenTitle, backButton) = createRefs()
                Text(
                    text = "Choose a city",
                    style = AppTheme.typography.h6,
                    color = AppTheme.colors.text,
                    modifier = Modifier.constrainAs(screenTitle) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                IconButton(
                    onClick = {
                        navHostController.navigateUp()
                        mainViewModel.clearCitiesList()
                    },
                    modifier = Modifier
                        .constrainAs(backButton) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = AppTheme.colors.uiSurface
                    )
                }
            }
        }
    ) {
        CityChooseScreenContent(
            listOfLargestCities = listOfLargestCities,
            mainViewModel = mainViewModel,
            navHostController = navHostController,
            countryId = countryId
        )
    }
}

@Composable
fun CityChooseScreenContent(
    listOfLargestCities: List<CityModel>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    countryId: String
) {
    Surface(
        color = AppTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (listOfLargestCities.isEmpty()) {
            CityChooseLoadingScreen()
            mainViewModel.getCitiesList(countryId = countryId)
        } else {
            CityChooseSuccessScreen(
                items = listOfLargestCities,
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityChooseSuccessScreen(
    items: List<CityModel>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    LazyColumn(
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.navigationBars,
            applyBottom = false,
            additionalTop = AppTheme.sizes.small,
            additionalBottom = AppTheme.sizes.bottomNavigationHeight
        )
    ) {
        stickyHeader {
            CityListItemHeader(title = "Top 50 largest cities")
        }
        items(items = items) { city ->
            LargeCityListItem(
                city = city,
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
        stickyHeader {
            CityListItemHeader(title = "Other cities")
        }
        items(items = listOf(items.first())) { city ->
            OtherCityListItem(countryId = city.countryId, navHostController = navHostController)
        }
    }
}

@Composable
fun CityChooseLoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.text
        )
    }
}

@Composable
fun CityListItemHeader(
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Divider(
            color = AppTheme.colors.card,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Card(
            backgroundColor = AppTheme.colors.card,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = AppTheme.colors.text,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.listHeader,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Divider(
            color = AppTheme.colors.card,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun LargeCityListItem(
    city: CityModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = {
                mainViewModel.addLocationCityAndFetchWeather(city = city)
                navHostController.navigate(Screens.LocationsScreen.routeName) {
                    launchSingleTop = true
                    popUpTo(navHostController.graph.startDestinationId)
                }
            })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${city.name} - ${city.population}",
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.listItem,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun OtherCityListItem(
    countryId: String,
    navHostController: NavHostController
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = {
                navHostController.navigate("${Screens.CitySearchScreen.routeName}/$countryId")
            })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Choose another city",
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.listItem,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}
