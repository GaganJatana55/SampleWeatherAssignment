package com.example.jumpingmind_assignment.feature.splash.repo

import android.app.Application
import android.content.Context
import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreI
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val dataStore: DataStoreI,

) {
    fun getUserLoggedIn() =
        dataStore.isUserLoggedIn()
}