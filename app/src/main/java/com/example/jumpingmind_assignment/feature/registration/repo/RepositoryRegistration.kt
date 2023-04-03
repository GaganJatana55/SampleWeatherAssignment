package com.example.jumpingmind_assignment.feature.registration.repo

import android.content.ContentValues
import android.content.Intent
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jumpingmind_assignment.extensions.toUserEntitty
import com.example.jumpingmind_assignment.feature.base.BaseRepository
import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.common.roomStorage.UsersDao
import com.example.jumpingmind_assignment.feature.registration.data.LoginRequest
import com.example.jumpingmind_assignment.feature.registration.data.LoginSignUpResponse
import com.example.jumpingmind_assignment.feature.registration.data.SignUpRequest
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class RepositoryRegistration @Inject constructor(
    private val userDatabase: UsersDao,
    private val internetConnectivity: InternetConnectivity
) : BaseRepository(internetConnectivity), RepoRegistrationInterface {

    override fun login(request: LoginRequest): Flow<RepoResult<LoginSignUpResponse?>> {
        return safeDBCall({
            userDatabase.getUserProfile(request.email)
        }, {
            try {
                if (it == null) {
                    LoginSignUpResponse(false, "User Not Found.")
                } else if (it.password == request.password) {
                    LoginSignUpResponse(true, "")
                } else {
                    LoginSignUpResponse(false, "Invalid password.")
                }
            } catch (e: Exception) {
                LoginSignUpResponse(false, "User Not Found.")
            }


        })

    }


    override fun signUp(request: SignUpRequest): Flow<RepoResult<LoginSignUpResponse?>> {
        return safeDBCall({ userDatabase.userExists(request.email) }, {
            if (!it) {
                LoginSignUpResponse(true, "")
            } else {
                LoginSignUpResponse(false, "User Already Exist")
            }
        })

    }

    override fun insertUser(request: SignUpRequest): Flow<RepoResult<LoginSignUpResponse?>> {
        return safeDBCall({
            userDatabase.insertUser(request.toUserEntitty())
        }, {
            LoginSignUpResponse(true, "")
        })
    }


}