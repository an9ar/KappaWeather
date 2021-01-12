package com.an9ar.kappaweather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryModel (
    @PrimaryKey
    val objectId: String,
    val name: String
)