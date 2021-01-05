package com.an9ar.kappaweather.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.network.CountriesListResponse
import com.an9ar.kappaweather.network.toCountryModel
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun LocationScreen(
    mainViewModel: MainViewModel
) {
    mainViewModel.getCountriesList()
    val listOfCountries = mainViewModel.countriesList.observeAsState(initial = emptyList())
    LocationScreenContent(items = listOfCountries.value)
}

@Composable
fun LocationScreenContent(
    items: List<CountriesListResponse>
) {
    LazyColumn {
        items(items) { country ->
            CountryListItem(country = country.toCountryModel())
        }
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
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = {

            })
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                data = country.flagUrl,
                fadeIn = true,
                contentScale = ContentScale.FillBounds,
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = AppTheme.colors.text,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                modifier = Modifier.height(90.dp).width(120.dp)
            )
            Text(
                text = country.name,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}