package com.example.jumpingmind_assignment.feature.common.roomStorage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jumpingmind_assignment.feature.common.entities.UserProfile
import com.example.jumpingmind_assignment.feature.dashboard.data.WeatherResponse

@Database(
    entities = [
        UserProfile::class,
        WeatherResponse::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDAo(): UsersDao

}
