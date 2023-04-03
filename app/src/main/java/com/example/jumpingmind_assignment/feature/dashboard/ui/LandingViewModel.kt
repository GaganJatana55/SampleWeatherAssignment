package com.example.jumpingmind_assignment.feature.dashboard.ui

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreI
import com.example.jumpingmind_assignment.feature.common.entities.UserProfile
import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.dashboard.data.ListItem
import com.example.jumpingmind_assignment.feature.dashboard.repo.RepoInterface
import com.example.jumpingmind_assignment.utils.singleEvent.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LandingViewModel @Inject constructor(
    private val repoInterface: RepoInterface,
    private val dataStoreI: DataStoreI,
    private val context: Application
) : ViewModel() {

    //logged in user mail
    var email = ""

    //Lat Long of city for API
    private var latLong = Pair(22.5726, 88.3639)

    val action = SingleLiveEvent<Action>()

    /**
     * user profile
     */
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile

    /**
     * weather response
     */
    private val _weather = MutableLiveData<List<ListItem>>()
    val weather: LiveData<List<ListItem>> = _weather

    /**
     * selected Item
     */
    private val _weather_selected = MutableLiveData<ListItem>()
    val data: LiveData<ListItem> = _weather_selected
    private val _weather_selected_drawable = MutableLiveData<Drawable>()
    val dataDrawable: LiveData<Drawable> = _weather_selected_drawable

    /**
     * error
     */

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * loading handling
     */
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun showLoading() {
        _loading.postValue(true)
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
        }, 2000)
    }

    fun hideLoading() {
        _loading.postValue(false)
    }

    /**
     * get logged in email to fetch profile
     */
    init {
        viewModelScope.launch {
            dataStoreI.isUserLoggedIn().collect {
                email = it
                fetchProfileData()
            }
        }
    }

    fun showSelectedItem(item: ListItem) {
        _weather_selected.postValue(item)
        _weather_selected_drawable.postValue(ContextCompat.getDrawable(context, item.getIcon()))
    }

    fun searchCityFromGeocoder() {
        action.postValue(Action.searchCity)
    }

    fun logOut() {
        viewModelScope.launch {
            dataStoreI.clearDataStore()
        }
        action.postValue(Action.logout)

    }

    fun fetchProfileData() {
        viewModelScope.launch {
            repoInterface.getProfileData(email).collect { result ->
                when (result) {
                    is RepoResult.Success -> {
                        result.data.let {
                            _userProfile.postValue(it)
                            fetchWeatherData(latLong.first, latLong.second)
                        }
                    }
                    is RepoResult.Error -> {

                    }
                    is RepoResult.Loading -> {

                    }
                    is RepoResult.Exception -> {

                    }

                }
            }
        }
    }

    fun fetchWeatherData(lat: Double, long: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repoInterface.getWeatherData(lat, long).collect { result ->
                when (result) {
                    is RepoResult.Success -> {
                        result.data.let { data ->
                            if (data == null) {
                                _weather.postValue(listOf())
                            } else {
                                _weather.postValue(data.list)
                            }
                        }
                    }
                    is RepoResult.Error -> {
                        _error.postValue(result.data)
                    }
                    is RepoResult.Loading -> {
                        _loading.postValue(result.loadingStatus)
                    }
                    is RepoResult.Exception -> {
                        _error.postValue(result.throwable.message)
                    }

                }
            }
        }
    }


    enum class Action {
        searchCity, logout
    }
}

