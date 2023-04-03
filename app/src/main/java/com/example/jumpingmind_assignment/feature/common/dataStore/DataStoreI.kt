package com.example.jumpingmind_assignment.feature.common.dataStore

import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.Flow

interface DataStoreI {

    fun isUserLoggedIn(): Flow<String>
    fun clearDataStore()
    suspend fun setUserLoggedIn( id: String)
}