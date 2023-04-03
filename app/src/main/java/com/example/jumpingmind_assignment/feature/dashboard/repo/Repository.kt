package com.example.jumpingmind_assignment.feature.dashboard.repo

import com.example.jumpingmind_assignment.feature.base.BaseRepository
import com.example.jumpingmind_assignment.feature.common.roomStorage.UsersDao
import com.example.jumpingmind_assignment.feature.dashboard.api.APIService
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: APIService,
    private val usersDao: UsersDao,
    internetConnectivity: InternetConnectivity
) : BaseRepository(internetConnectivity), RepoInterface {

    override suspend fun getWeatherData(lat: Double, long: Double) = merge(
        safeApiCall({ service.getWeatherReport(lat, long) }) {
            usersDao.deleteWeatherData()
            usersDao.insertWeather(it)
        },
        getWeatherFromCACHE()
    )

    override suspend fun getProfileData(email: String) =
        safeDBCall({ usersDao.getUserProfile(email) }, {
            it!!
        })


    private fun getWeatherFromCACHE() = safeDBCall({ usersDao.getWeatherResponse() }, {
        it
    })

}