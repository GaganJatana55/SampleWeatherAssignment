package com.example.jumpingmind_assignment.feature.dashboard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ListItem>,
    val message: Int,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)