package com.example.jumpingmind_assignment.feature.common.roomStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jumpingmind_assignment.feature.common.entities.UserProfile
import com.example.jumpingmind_assignment.feature.dashboard.data.WeatherResponse

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserProfile)

    @Query("SELECT * FROM user_table WHERE email = :pEmail")
    fun getUserProfile(pEmail: String): UserProfile?

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE email = :pEmail)")
    fun userExists(pEmail: String): Boolean

    /**
     * weather queries
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(user: WeatherResponse)

    @Query("SELECT * FROM weather LIMIT 1")
    fun getWeatherResponse(): WeatherResponse?

    @Query("DELETE FROM weather")
    fun deleteWeatherData()

}