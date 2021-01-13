package com.an9ar.kappaweather.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    mainViewModel: MainViewModel
) {
    val listOfCountries = mainViewModel.countriesList.observeAsState(
        initial = Resource(
            status = Resource.Status.LOADING,
            data = emptyList(),
            message = ""
        )
    )
    CountryChooseScreenContent(listOfCountries)
}

@Composable
fun CountryChooseScreenContent(
    listOfCountries: State<Resource<List<CountryModel>>>
) {
    Surface(color = AppTheme.colors.warning) {
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
}

@Composable
fun CountryChooseSuccessScreen(
    items: List<CountryModel>
) {
    LazyColumn(
        contentPadding = AmbientWindowInsets.current.systemBars
            .toPaddingValues(bottom = false)
            .add(bottom = AppTheme.sizes.bottomNavigationHeight)
    ) {
        items(items) { country ->
            CountryListItem(country = country)
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
fun CountryListItem(
    country: CountryModel
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
                text = country.name,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}