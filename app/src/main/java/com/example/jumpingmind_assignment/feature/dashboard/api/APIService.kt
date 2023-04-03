package com.example.jumpingmind_assignment.feature.dashboard.api

import com.example.jumpingmind_assignment.feature.dashboard.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("forecast")
    suspend fun getWeatherReport(@Query("lat") lat:Double,@Query("lon")long: Double,@Query("appid")api:String="a48565941e262ee0889cdd93bea12004", @Query("units") units:String="metric"): Response<WeatherResponse>
}
