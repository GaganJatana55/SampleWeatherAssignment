package com.example.jumpingmind_assignment.feature.splash.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreI
import com.example.jumpingmind_assignment.feature.splash.repo.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private var dataStoreI: DataStoreI) :
    ViewModel() {
    private var _loggedId = MutableLiveData<String>()
    val loggedId: LiveData<String> = _loggedId



     fun getUserLoggedIn() {
       viewModelScope.launch {
            dataStoreI.isUserLoggedIn().collect {
                _loggedId.postValue(it)
            }
        }
    }
}