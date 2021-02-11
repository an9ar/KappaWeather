package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import dev.chrisbanes.accompanist.insets.toPaddingValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.constraintlayout.compose.ConstraintLayout
import dev.chrisbanes.accompanist.insets.LocalWindowInsets

@Composable
fun CitySearchScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    countryId: String
) {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val searchedCityQuery = mainViewModel.citySearchQuery.observeAsState(initial = "")
    val listOfSearchedCities =
        mainViewModel.getCitiesListBySearch(countryId = countryId, searchedCityQuery.value)
            .observeAsState(
                initial = Resource(
                    status = Resource.Status.COMPLETED,
                    data = emptyList(),
                    message = ""
                )
            )
    Scaffold(
        topBar = {
            Column {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.toolbar)
                        .padding(LocalWindowInsets.current.statusBars.toPaddingValues())
                        .preferredHeight(AppTheme.sizes.appBarHeight)
                ) {
                    val (screenTitle, backButton) = createRefs()
                    Text(
                        text = "Other cities",
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
                        onClick = { navHostController.navigateUp() },
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
                SearchBar(
                    scope = coroutineScope,
                    onDebouncingQueryTextChange = { queryText ->
                        if (!queryText.isNullOrEmpty()) {
                            mainViewModel.setCitySearchQuery(query = queryText)
                        }
                    },
                    initValue = searchedCityQuery.value
                )
            }
        }
    ) {
        CitySearchScreenContent(
            listOfSearchedCities = listOfSearchedCities,
            mainViewModel = mainViewModel,
            navHostController = navHostController
        )
    }
}

@Composable
fun SearchBar(
    scope: CoroutineScope,
    onDebouncingQueryTextChange: (String?) -> Unit,
    initValue: String = ""
) {
    ConstraintLayout(
        modifier = Modifier
            .background(AppTheme.colors.toolbar)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = AppTheme.colors.uiSurface,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
    ) {
        val (searchBar, clearButton) = createRefs()
        var searchedCity by remember { mutableStateOf(TextFieldValue(initValue)) }
        val focusRequester = remember { FocusRequester() }
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                focusRequester.requestFocus()
            }
            .padding(16.dp)
            .constrainAs(searchBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 8.dp)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
            BasicTextField(
                value = searchedCity,
                onValueChange = {
                    searchedCity = it
                    scope.launch {
                        delay(500)
                        onDebouncingQueryTextChange(it.text)
                    }
                },
                singleLine = true,
                textStyle = AppTheme.typography.queryText,
                cursorColor = AppTheme.colors.text,
                modifier = Modifier.focusRequester(focusRequester = focusRequester)
            )
        }

        IconButton(
            onClick = { searchedCity = TextFieldValue("") },
            modifier = Modifier
                .constrainAs(clearButton) {
                    top.linkTo(searchBar.top)
                    bottom.linkTo(searchBar.bottom)
                    end.linkTo(searchBar.end, 8.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = AppTheme.colors.uiSurface
            )
        }
    }
}

@Composable
fun CitySearchScreenContent(
    listOfSearchedCities: State<Resource<List<CityModel>>>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        when (listOfSearchedCities.value.status) {
            Resource.Status.SUCCESS -> {
                listOfSearchedCities.value.data?.let {
                    SearchedCitiesSuccessScreen(
                        items = it,
                        mainViewModel = mainViewModel,
                        navHostController = navHostController
                    )
                }
            }
            Resource.Status.LOADING -> {
                CityChooseLoadingScreen()
            }
            Resource.Status.ERROR -> {
                log("ERROR - ${listOfSearchedCities.value.message}")
            }
            Resource.Status.COMPLETED -> {
                log("EMPTY LIST")
            }
        }
    }
}

@Composable
fun SearchedCitiesSuccessScreen(
    items: List<CityModel>,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    LazyColumn(
        contentPadding = LocalWindowInsets.current.navigationBars
            .toPaddingValues(
                bottom = false,
                additionalTop = AppTheme.sizes.small,
                additionalBottom = AppTheme.sizes.bottomNavigationHeight
            )
    ) {
        items(items = items) { city ->
            SearchedCityListItem(
                city = city,
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun SearchedCityListItem(
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
                mainViewModel.addLocationCity(city = city)
                val weatherSavingState = mainViewModel.getLocationWeather(
                    objectId = System.currentTimeMillis(),
                    objectName = city.name,
                    latitude = city.lat,
                    longitude = city.lng
                )
                when (weatherSavingState) {
                    Resource.Status.SUCCESS -> {
                        navHostController.navigate(Screens.LocationsScreen.routeName) {
                            launchSingleTop = true
                            popUpTo = navHostController.graph.startDestination
                        }
                    }
                    Resource.Status.ERROR -> {
                        log("FETCHING ERROR")
                    }
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
