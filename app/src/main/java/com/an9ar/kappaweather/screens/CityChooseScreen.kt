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
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.add
import dev.chrisbanes.accompanist.insets.toPaddingValues

@Composable
fun CityChooseScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    countryId: String
) {
    val listOfCities = mainViewModel.getCitiesList(countryId = countryId).observeAsState(
        initial = Resource(
            status = Resource.Status.LOADING,
            data = emptyList(),
            message = ""
        )
    )
    log("countryId - $countryId")
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = AppTheme.colors.toolbar) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
        CityChooseScreenContent(listOfCities)
    }
}

@Composable
fun CityChooseScreenContent(
    listOfCities: State<Resource<List<CityModel>>>
) {
    Surface(
            color = AppTheme.colors.background,
            modifier = Modifier.fillMaxSize()
    ) {
        when (listOfCities.value.status) {
            Resource.Status.SUCCESS -> {
                listOfCities.value.data?.let {
                    CityChooseSuccessScreen(items = it)
                }
                log("SUCCESS - ${listOfCities.value.data}")
                log("SUCCESS MSG - ${listOfCities.value.message}")
            }
            Resource.Status.LOADING -> {
                CityChooseLoadingScreen()
                log("LOADING")
                log("LOADING MSG - ${listOfCities.value.message}")
            }
            Resource.Status.ERROR -> {
                log("ERROR")
                log("ERROR MSG - ${listOfCities.value.message}")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityChooseSuccessScreen(
    items: List<CityModel>
) {
    val alphabeticalList = items.groupBy { it.name.first() }
    LazyColumn(
        contentPadding = AmbientWindowInsets.current.systemBars
            .toPaddingValues(bottom = false)
            .add(bottom = AppTheme.sizes.bottomNavigationHeight)
    ) {
        alphabeticalList.forEach { countryMapItem ->
            stickyHeader {
                CityListItemHeader(title = countryMapItem.key.toString())
            }
            items(countryMapItem.value) { city ->
                CityListItem(city = city)
            }
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
fun CityListItem(
    city: CityModel
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = {
                log("CLICKED - $city")
            })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = city.name,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.listItem,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}
