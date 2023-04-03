package com.example.jumpingmind_assignment.feature.registration.data

data class LoginRequest(var email: String, var password: String)

data class LoginSignUpResponse(var status: Boolean,var error:String)

data class SignUpRequest(
    var email: String,
    var password: String,
    var userName: String,
    var profilePicture: String,
    var bio: String
)

data class GetProfileResponse(
    var email: String,
    var userName: String,
    var profilePicture: String,
    var bio: String
)
