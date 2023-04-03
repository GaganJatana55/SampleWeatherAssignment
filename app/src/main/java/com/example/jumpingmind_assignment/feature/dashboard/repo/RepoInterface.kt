package com.example.jumpingmind_assignment.feature.dashboard.repo

import com.example.jumpingmind_assignment.feature.common.entities.UserProfile
import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.dashboard.data.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface RepoInterface {
     suspend fun getWeatherData( lat:Double,long: Double): Flow<RepoResult<WeatherResponse?>>
     suspend fun getProfileData( email: String): Flow<RepoResult<UserProfile>>
}