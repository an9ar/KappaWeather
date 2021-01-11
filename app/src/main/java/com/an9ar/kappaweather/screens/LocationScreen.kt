package com.an9ar.kappaweather.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun LocationScreen(
        mainViewModel: MainViewModel
) {
    mainViewModel.getCitiesList()
    val listOfCities = mainViewModel.citiesList.observeAsState(initial = emptyList())
    LocationScreenContent(items = listOfCities.value)
}

@Composable
fun LocationScreenContent(
        items: List<CityDTO>
) {
    LazyColumn {
        items(items) { city ->
            CountryListItem(city = city.toCityModel())
        }
    }
}

@Composable
fun CountryListItem(
    city: CityModel
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
            Text(
                    text = city.city,
                    color = AppTheme.colors.text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}