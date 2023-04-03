package com.example.jumpingmind_assignment.feature.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_table")
class UserProfile (

    var email: String,
    var password: String,
    var userName: String,
    var profilePicture: String,
    var bio: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
        )