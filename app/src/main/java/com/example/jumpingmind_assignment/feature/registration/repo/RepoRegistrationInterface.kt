package com.example.jumpingmind_assignment.feature.registration.repo

import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.registration.data.LoginRequest
import com.example.jumpingmind_assignment.feature.registration.data.LoginSignUpResponse
import com.example.jumpingmind_assignment.feature.registration.data.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface RepoRegistrationInterface {
     fun login( request: LoginRequest): Flow<RepoResult<LoginSignUpResponse?>>
     fun signUp( request: SignUpRequest): Flow<RepoResult<LoginSignUpResponse?>>
     fun insertUser( request: SignUpRequest): Flow<RepoResult<LoginSignUpResponse?>>
}