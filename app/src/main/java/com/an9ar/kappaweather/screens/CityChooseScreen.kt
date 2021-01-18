package com.an9ar.kappaweather.screens

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun CityChooseScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    countryId: String
) {
    mainViewModel.getCitiesList(countryId = countryId)
    val listOfCities = mainViewModel.countriesList.observeAsState(
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
                        onClick = { /*navHostController.navigateUp()*/ },
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

    }
}

/*
@Composable
fun CityChooseScreenContent(
    listOfCities: State<Resource<List<CountryModel>>>
) {
    Surface(color = AppTheme.colors.background) {
        when (listOfCountries.value.status) {
            Resource.Status.SUCCESS -> {
                listOfCountries.value.data?.let {
                    CountryChooseSuccessScreen(items = it)
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
}*/
