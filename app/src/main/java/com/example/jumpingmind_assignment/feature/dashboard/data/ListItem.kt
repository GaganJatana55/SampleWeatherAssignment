package com.example.jumpingmind_assignment.feature.dashboard.data

import android.text.format.DateUtils
import com.example.jumpingmind_assignment.R
import java.text.SimpleDateFormat
import java.util.*

data class ListItem(
    val clouds: Clouds,
    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun getDateString(): String = try {
        val postFormater = SimpleDateFormat("MMMM dd")
        postFormater.format((Date().apply {
            time = dt * 1000
        }))
    } catch (e: Exception) {
        dt_txt
    }

    fun getTimeString(): String = try {
        val postFormater = SimpleDateFormat("hh:mm aa")
        postFormater.format((Date().apply {
            time = dt * 1000
        }))
    } catch (e: Exception) {
        dt_txt
    }

    fun getIcon():Int{
       return if  (getWeatherData().lowercase(Locale.getDefault()) =="clouds"){
            R.drawable.baseline_cloud_24
        }else if (getWeatherData().lowercase(Locale.getDefault()) =="rain"){
            R.drawable.baseline_grain_24
        }else {
            R.drawable.baseline_wb_sunny_24
        }
    }
    fun getDayString(): String = try {
        val date = (Date().apply {
            time = dt * 1000
        })
        if (DateUtils.isToday(date.time)) {
            "Today"
        } else if (DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS)) {
            "Tomorrow"
        } else {

            val postFormater = SimpleDateFormat("EEEE")
            postFormater.format(date)
        }
    } catch (e: Exception) {
        dt_txt
    }


    fun getCloudsPercentage() = clouds.all.toString()
    fun getWindSpeed() = wind.speed.toString()
    fun getWindDegree() = wind.deg.toString()
    fun getTemperature() = main.temp.toString()+"°"
    fun getTemperatureMax() = main.temp_max.toString()+"°"
    fun getTemperatureMin() = main.temp_min.toString()+"°"
    fun getPrecip() = (pop * 100).toInt().toString()+"%"
    fun getFeelsLike() = main.feels_like.toString()+"°"
    fun getHumidity() = main.humidity.toString()
    fun getWeatherData(): String {
        return if (weather.isNotEmpty()) {
            weather[0].main
        } else {
            ""
        }
    }

    fun getVisibilityString() = visibility.toString()

    fun getWeatherDescription(): String {
        return if (weather.isNotEmpty()) {
            weather[0].description
        } else {
            ""
        }
    }

    fun getWeatherIcon(): String {
        return if (weather.isNotEmpty()) {
            weather[0].icon
        } else {
            ""
        }
    }


}