package com.example.jumpingmind_assignment.extensions

import com.example.jumpingmind_assignment.feature.registration.data.SignUpRequest
import com.example.jumpingmind_assignment.feature.common.entities.UserProfile

fun SignUpRequest.toUserEntitty(): UserProfile {
    return UserProfile(email,password,userName,profilePicture, bio)
}