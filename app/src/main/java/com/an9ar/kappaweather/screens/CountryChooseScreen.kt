package com.an9ar.kappaweather.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.add
import dev.chrisbanes.accompanist.insets.toPaddingValues

@Composable
fun CountryChooseScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    val listOfCountries = mainViewModel.countriesList.observeAsState(
        initial = Resource(
            status = Resource.Status.LOADING,
            data = emptyList(),
            message = ""
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = AppTheme.colors.toolbar) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (screenTitle, backButton) = createRefs()
                    Text(
                        text = "Choose a country",
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
                            tint = AppTheme.colors.uiSurface
                        )
                    }
                }
            }
        }
    ) {
        CountryChooseScreenContent(listOfCountries, navHostController)
    }
}

@Composable
fun CountryChooseScreenContent(
    listOfCountries: State<Resource<List<CountryModel>>>,
    navHostController: NavHostController
) {
    Surface(color = AppTheme.colors.background) {
        when (listOfCountries.value.status) {
            Resource.Status.SUCCESS -> {
                listOfCountries.value.data?.let {
                    CountryChooseSuccessScreen(items = it, navHostController = navHostController)
                }
                log("SUCCESS")
            }
            Resource.Status.LOADING -> {
                CountryChooseLoadingScreen()
                log("LOADING")
            }
            Resource.Status.ERROR -> {
                log("ERROR")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryChooseSuccessScreen(
    items: List<CountryModel>,
    navHostController: NavHostController
) {
    val alphabeticalList = items.groupBy { it.name.first() }
    LazyColumn(
        contentPadding = AmbientWindowInsets.current.systemBars
            .toPaddingValues(bottom = false)
            .add(bottom = AppTheme.sizes.bottomNavigationHeight)
    ) {
        alphabeticalList.forEach { countryMapItem ->
            stickyHeader {
                CountryListItemHeader(title = countryMapItem.key.toString())
            }
            items(countryMapItem.value) { country ->
                CountryListItem(country = country, navHostController = navHostController)
            }
        }
    }
}

@Composable
fun CountryChooseLoadingScreen() {
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
fun CountryListItemHeader(
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Divider(
            color = AppTheme.colors.card,
            modifier = Modifier.weight(1f).padding(start = 16.dp)
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
            modifier = Modifier.weight(1f).padding(end = 16.dp)
        )
    }
}

@Composable
fun CountryListItem(
    country: CountryModel,
    navHostController: NavHostController
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = {
                navHostController.navigate("${Screens.CityChooseScreen.routeName}/${country.objectId}")
            })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = country.name,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.listItem,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}