package com.example.jumpingmind_assignment.feature.common.roomStorage

import androidx.room.TypeConverter
import com.example.jumpingmind_assignment.feature.dashboard.data.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    val gson by lazy {
        Gson()
    }
    @TypeConverter
    fun toCity(value: String) = gson.fromJson(value, City::class.java)

    @TypeConverter
    fun fromCity(value: City) = gson.toJson(value)

    @TypeConverter
    fun toCoord(value: String) = gson.fromJson(value, Coord::class.java)

    @TypeConverter
    fun fromCoord(value: Coord) = gson.toJson(value)

    @TypeConverter
    fun toClouds(value: String) = gson.fromJson(value, Clouds::class.java)

    @TypeConverter
    fun fromClouds(value: Clouds) = gson.toJson(value)

    @TypeConverter
    fun fromMain(value: Main) = gson.toJson(value)

    @TypeConverter
    fun toMain(value: String) = gson.fromJson(value, Main::class.java)

    @TypeConverter
    fun fromSys(value: Sys) = gson.toJson(value)

    @TypeConverter
    fun toSys(value: String) = gson.fromJson(value, Sys::class.java)


    @TypeConverter
    fun toWeather(value: String) = gson.fromJson(value, Weather::class.java)

    @TypeConverter
    fun fromWeather(value: Weather) = gson.toJson(value)

    @TypeConverter
    fun toWind(value: String) = gson.fromJson(value, Wind::class.java)

    @TypeConverter
    fun fromWind(value: Wind) = gson.toJson(value)

    @TypeConverter
    fun toListItem(value: String) = gson.fromJson(value, ListItem::class.java)

    @TypeConverter
    fun fromListItem(value: ListItem) = gson.toJson(value)


    @TypeConverter
    fun fromString(value: String): List<ListItem> {
        val listType: Type = object : TypeToken<List<ListItem>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<ListItem>): String {

        return gson.toJson(list)
    }

}


