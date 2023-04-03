package com.example.jumpingmind_assignment.feature.common.dataStore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_pref")

class DataStoreImpl @Inject constructor(private val context: Context) : DataStoreI {
    override fun isUserLoggedIn(): Flow<String> {
        val wrappedKey = PreferencesKeys.SHOW_COMPLETED

        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[wrappedKey] ?: ""
        }
        return valueFlow
    }

    override fun clearDataStore() {
        runBlocking {
            context.dataStore.edit {
                it.clear()
            }
        }
    }

    override suspend fun setUserLoggedIn(id: String) {
        val wrappedKey = PreferencesKeys.SHOW_COMPLETED
        context.dataStore.edit {
            it[wrappedKey] = id
        }
    }


    private object PreferencesKeys {

        val SHOW_COMPLETED = stringPreferencesKey("loggedIn")
    }
}