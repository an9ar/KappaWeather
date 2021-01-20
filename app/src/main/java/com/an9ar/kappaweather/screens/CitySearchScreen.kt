package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CitySearchScreen(
        mainViewModel: MainViewModel,
        navHostController: NavHostController,
        countryId: String
) {
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    Scaffold(
            topBar = {
                Column() {
                    TopAppBar(backgroundColor = AppTheme.colors.toolbar) {
                        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                                        tint = AppTheme.colors.uiSurface
                                )
                            }
                        }
                    }
                    SearchBar(
                            scope = coroutineScope,
                            onDebouncingQueryTextChange = { queryText ->
                                if (!queryText.isNullOrEmpty()) {

                                }
                            }
                    )
                }
            }
    ) {
        CitySearchScreenContent()
    }
}

@Composable
fun SearchBar(
        scope: CoroutineScope,
        onDebouncingQueryTextChange: (String?) -> Unit
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
        var searchedCity by remember { mutableStateOf(TextFieldValue("")) }
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
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .constrainAs(searchBar) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start, 8.dp)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
        )
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
                    tint = AppTheme.colors.uiSurface
            )
        }
    }
}

@Composable
fun CitySearchScreenContent() {
    Column(
            modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)
    ) {
        /*when (listOfLargestCities.value.status) {
            Resource.Status.SUCCESS -> {
                listOfLargestCities.value.data?.let {
                    //CityChooseSuccessScreen(items = it)
                }
                log("SUCCESS - ${listOfLargestCities.value.data}")
                log("SUCCESS MSG - ${listOfLargestCities.value.message}")
            }
            Resource.Status.LOADING -> {
                //CityChooseLoadingScreen()
                log("LOADING")
                log("LOADING MSG - ${listOfLargestCities.value.message}")
            }
            Resource.Status.ERROR -> {
                log("ERROR")
                log("ERROR MSG - ${listOfLargestCities.value.message}")
            }
        }*/
    }
}
